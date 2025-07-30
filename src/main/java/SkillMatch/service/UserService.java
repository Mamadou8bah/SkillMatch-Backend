package SkillMatch.service;

import SkillMatch.dto.LoginRequest;
import SkillMatch.dto.LoginResponse;
import SkillMatch.dto.RegisterRequest;
import SkillMatch.dto.UserDTO;
import SkillMatch.exception.UserAlreadyExistException;
import SkillMatch.model.Candidate;
import SkillMatch.model.Employer;
import SkillMatch.model.SecureToken;
import SkillMatch.model.Token;
import SkillMatch.model.User;
import SkillMatch.repository.TokenRepo;
import SkillMatch.repository.UserRepo;
import SkillMatch.util.AccountVerificationEmailContext;
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
        return repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User deleteUser(long id){
        User user= repo.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        repo.delete(user);
        return user;
    }

    public User updateUser(long id,User newUser){
        User user=repo.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
        user.setCandidate(newUser.getCandidate());
        user.setEmail(newUser.getEmail());
        user.setFullName(newUser.getFullName());
        user.setPassword(newUser.getPassword());

        repo.save(user);
        return  user;
    }

    public User register(RegisterRequest request, Role role) throws UserAlreadyExistException {
       
        if (request == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        String email = request.getEmail().trim().toLowerCase();
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
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
        if(user==null) throw new  IllegalArgumentException("No User With that email");

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
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
            throw new RuntimeException("Not Authenticated");
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

}
