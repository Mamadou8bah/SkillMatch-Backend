package SkillMatch.service;

import SkillMatch.dto.UserDTO;
import SkillMatch.model.User;
import SkillMatch.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    public List<UserDTO>getUsers(){
        List<User> users= repo.findAll();
        List<UserDTO> userDTOS=new ArrayList<>();
        for(User user:users){
            userDTOS.add(new UserDTO(user.getFullName(),user.getRole().toString()));
        }
        return userDTOS;
    }

    public User register(User user){
        return repo.save(user);
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
}
