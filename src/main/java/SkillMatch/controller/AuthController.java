package SkillMatch.controller;


import SkillMatch.dto.LoginRequest;
import SkillMatch.dto.LoginResponse;
import SkillMatch.dto.PasswordResetRequestDTO;
import SkillMatch.dto.PasswordResetDTO;
import SkillMatch.dto.RegisterRequest;
import SkillMatch.exception.UserAlreadyExistException;
import SkillMatch.model.User;
import SkillMatch.service.CandidateService;
import SkillMatch.service.EmployerService;
import SkillMatch.service.UserService;
import SkillMatch.util.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService service;

    @Autowired
    EmployerService employerService;

    @Autowired
    CandidateService candidateService;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?>register(@Valid @RequestBody RegisterRequest request, @RequestParam Role role)throws UserAlreadyExistException {
        User registeredUser = service.register(request, role);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody LoginRequest request){
        LoginResponse response=service.login(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/me")
    public ResponseEntity<?>getCurrentUser(){
        User user= service.getLogInUser();
        return ResponseEntity.ok(user);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        service.logout(request.getHeader("Authorization"));
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String token) {
        try {
            boolean isVerified = service.verifyAccount(token);
            if (isVerified) {
                return ResponseEntity.ok("Account verified successfully! You can now log in.");
            } else {
                return ResponseEntity.badRequest().body("Invalid or expired verification token.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Verification failed: " + e.getMessage());
        }
    }


    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordReset(
            @Valid @RequestBody PasswordResetRequestDTO request) {
        service.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok("If your email is registered, you will receive password reset instructions");
    }

    @GetMapping("/password-reset/validate/{token}")
    public ResponseEntity<String> validateResetToken(@PathVariable String token) {
        boolean isValid = service.validateResetToken(token);
        if (isValid) {
            return ResponseEntity.ok("Reset token is valid");
        } else {
            return ResponseEntity.badRequest().body("Reset token is invalid or expired");
        }
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<String> confirmPasswordReset(
            @Valid @RequestBody PasswordResetDTO request) {
        service.resetPassword(request);
        return ResponseEntity.ok("Password has been reset successfully");
    }


}
