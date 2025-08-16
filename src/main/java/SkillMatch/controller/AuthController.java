package SkillMatch.controller;

import SkillMatch.dto.ApiResponse;
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

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request, @RequestParam Role role)throws UserAlreadyExistException {
        User registeredUser = service.register(request, role);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully. Please check your email for verification.", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = service.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(){
        User user = service.getLogInUser();
        return ResponseEntity.ok(ApiResponse.success("Current user retrieved successfully", user));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        service.logout(request.getHeader("Authorization"));
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/register/verify")
    public ResponseEntity<ApiResponse<String>> verifyAccount(@RequestParam String token) {
        try {
            boolean isVerified = service.verifyAccount(token);
            if (isVerified) {
                return ResponseEntity.ok(ApiResponse.success("Account verified successfully! You can now log in."));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid or expired verification token."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Verification failed: " + e.getMessage()));
        }
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<ApiResponse<String>> requestPasswordReset(@Valid @RequestBody PasswordResetRequestDTO request) {
        service.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success("If your email is registered, you will receive password reset instructions"));
    }

    @GetMapping("/password-reset/validate/{token}")
    public ResponseEntity<ApiResponse<String>> validateResetToken(@PathVariable String token) {
        boolean isValid = service.validateResetToken(token);
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.success("Reset token is valid"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error("Reset token is invalid or expired"));
        }
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<ApiResponse<String>> confirmPasswordReset(@Valid @RequestBody PasswordResetDTO request) {
        service.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password has been reset successfully"));
    }


}
