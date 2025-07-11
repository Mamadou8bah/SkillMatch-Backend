package SkillMatch.service;

import SkillMatch.dto.LoginRequest;
import SkillMatch.dto.LoginResponse;
import SkillMatch.dto.UserDTO;
import SkillMatch.model.User;
import SkillMatch.repository.UserRepo;
import SkillMatch.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;
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

    public User register(User user) {

        if (repo.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
    public LoginResponse login(LoginRequest request){
        User user=repo.findByEmail(request.getEmail());
        if(user==null) throw new  IllegalArgumentException("No User With that email");

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return  new LoginResponse(jwtUtil.generateKey(user.getEmail()));
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

}
