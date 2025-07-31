package SkillMatch.service;

import SkillMatch.dto.LoginRequest;
import SkillMatch.dto.LoginResponse;
import SkillMatch.dto.PasswordResetRequestDTO;
import SkillMatch.dto.PasswordResetDTO;
import SkillMatch.dto.RegisterRequest;
import SkillMatch.dto.UserDTO;
import SkillMatch.exception.UserAlreadyExistException;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.exception.InvalidCredentialsException;
import SkillMatch.exception.AuthenticationException;
import SkillMatch.exception.ValidationException;
import SkillMatch.exception.TokenExpiredException;
import SkillMatch.exception.InvalidResetTokenException;
import SkillMatch.exception.PasswordMismatchException;
import SkillMatch.model.Candidate;
import SkillMatch.model.Employer;
import SkillMatch.model.SecureToken;
import SkillMatch.model.Token;
import SkillMatch.model.User;
import SkillMatch.repository.TokenRepo;
import SkillMatch.repository.UserRepo;
import SkillMatch.util.AccountVerificationEmailContext;
import SkillMatch.util.PasswordResetEmailContext;
import SkillMatch.util.JwtUtil;
import SkillMatch.util.Role;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    EmployerService employerService;

    @Autowired
    private EmailService emailService;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private SecureTokenService secureTokenService;
    @Autowired
    TokenRepo tokenRepo;

    @Value("${site.base.url}")
    private String baseUrl;
    @Autowired
    CandidateService candidateService;
    public List<UserDTO>getUsers(){
        List<User> users= repo.findAll();
        List<UserDTO> userDTOS=new ArrayList<>();
        for(User user:users){
            userDTOS.add(new UserDTO(user.getFullName(),user.getRole().toString()));
        }
        return userDTOS;
    }


    public User getUserById(long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
    public User deleteUser(long id){
        User user= repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        repo.delete(user);
        return user;
    }

    public User updateUser(long id,User newUser){
        User user=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setCandidate(newUser.getCandidate());
        user.setEmail(newUser.getEmail());
        user.setFullName(newUser.getFullName());
        user.setPassword(newUser.getPassword());

        repo.save(user);
        return  user;
    }

    public User register(RegisterRequest request, Role role) throws UserAlreadyExistException {
       
        if (request == null) {
            throw new ValidationException("Registration request cannot be null");
        }
        if (role == null) {
            throw new ValidationException("Role cannot be null");
        }
        String email = request.getEmail().trim().toLowerCase();
        if (email.isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }

        if (repo.findByEmail(request.getEmail()) != null) {
            throw new UserAlreadyExistException("User Already Exist");
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setProfilePicture(request.getProfilePicture());
        user.setLocation(request.getLocation());
        user.setRole(role);
        user.setActive(true);
        user.setAccountVerified(false);
        User saveUser=repo.save(user);
        if (role==Role.CANDIDATE) {
            Candidate candidate = new Candidate();
            candidate.setUser(saveUser);
            candidateService.addCandidate(candidate);
        } else if (role==Role.EMPLOYER) {
            Employer employer = new Employer();
            employer.setUser(saveUser);
            employerService.addEmployer(employer);
        }
        try {
            sendRegistrationConfirmationEmail(saveUser);
        } catch (Exception e) {
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
        return saveUser;
    }
    public LoginResponse login(LoginRequest request){
        User user=repo.findByEmail(request.getEmail());
        if(user==null) throw new ResourceNotFoundException("No user with that email");

        if(!user.isAccountVerified()) {
            throw new AuthenticationException("Please verify your email before logging in");
        }

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String jwtToken=jwtUtil.generateToken(user.getEmail());
        Token token=new Token();
        token.setToken(jwtToken);
        token.setRevoked(false);
        token.setExpired(false);
        token.setUser(user);
        tokenRepo.save(token);
        return  new LoginResponse(jwtToken);
    }
    public User getLogInUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null||!authentication.isAuthenticated()){
            throw new AuthenticationException("Not authenticated");
        }
        String email=authentication.getName();

        User user=repo.findByEmail(email);

        return user;
    }

    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String jwt = authHeader.substring(7);
        Token token=tokenRepo.findByToken(jwt);
        if(token!= null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepo.save(token);
        }
    }

    @Async
    public void sendRegistrationConfirmationEmail(User user){
        SecureToken secureToken=secureTokenService.createToken();
        secureToken.setUser(user);
        secureTokenService.saveToken(secureToken);

        AccountVerificationEmailContext context=new AccountVerificationEmailContext();
        context.init(user);
        context.setToken(secureToken.getToken());
        context.buildVerificationUrl(baseUrl,secureToken.getToken());
        try {
            emailService.sendMail(context);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyAccount(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (secureToken == null || secureToken.getExpiredAt().isBefore(java.time.LocalDateTime.now())) {
            return false;
        }
        
        User user = secureToken.getUser();
        user.setAccountVerified(true);
        repo.save(user);
        secureTokenService.removeToken(secureToken);
        return true;
    }

    public void requestPasswordReset(String email) {
        User user = repo.findByEmail(email);
        if (user == null) {
            return;
        }

        SecureToken resetToken = secureTokenService.createToken();
        resetToken.setUser(user);
        secureTokenService.saveToken(resetToken);

        try {
            sendPasswordResetEmail(user, resetToken.getToken());
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }
    }

    public boolean validateResetToken(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        return secureToken != null && secureToken.getExpiredAt().isAfter(java.time.LocalDateTime.now());
    }

    public void resetPassword(PasswordResetDTO resetDTO) {
        if (!resetDTO.getNewPassword().equals(resetDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        SecureToken secureToken = secureTokenService.findByToken(resetDTO.getToken());
        if (secureToken == null) {
            throw new InvalidResetTokenException("Invalid reset token");
        }

        if (secureToken.getExpiredAt().isBefore(java.time.LocalDateTime.now())) {
            throw new TokenExpiredException("Reset token has expired");
        }

        User user = secureToken.getUser();
        user.setPassword(encoder.encode(resetDTO.getNewPassword()));
        repo.save(user);

        secureTokenService.removeToken(secureToken);

        try {
            sendPasswordResetConfirmationEmail(user);
        } catch (Exception e) {
            System.err.println("Failed to send password reset confirmation email: " + e.getMessage());
        }
    }

    @Async
    public void sendPasswordResetEmail(User user, String token) {
        PasswordResetEmailContext context = new PasswordResetEmailContext();
        context.init(user);
        context.setToken(token);
        context.buildResetUrl(baseUrl, token);
        try {
            emailService.sendMail(context);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendPasswordResetConfirmationEmail(User user) {
        try {
            // For now, we'll skip the confirmation email or create a simple context
            // TODO: Create a dedicated PasswordResetConfirmationEmailContext
            System.out.println("Password reset successful for user: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send password reset confirmation email: " + e.getMessage());
        }
    }

}
