package SkillMatch.controller;


import SkillMatch.dto.LoginRequest;
import SkillMatch.dto.LoginResponse;
import SkillMatch.dto.RegisterRequest;
import SkillMatch.model.User;
import SkillMatch.service.UserService;
import SkillMatch.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService service;

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest request, @RequestParam Role role){
        User registeredUser = service.register(request);
        registeredUser.setRole(role);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest request){
        LoginResponse response=service.login(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/me")
    public ResponseEntity<?>getCurrentUser(){
        User user= service.getLogInUser();
        return ResponseEntity.ok(user);
    }

}
