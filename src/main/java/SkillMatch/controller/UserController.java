package SkillMatch.controller;

import SkillMatch.dto.UserDTO;
import SkillMatch.model.User;
import SkillMatch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public List<UserDTO> getUsers(){
        return service.getUsers();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
       return service.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable long id) {
        return service.deleteUser(id);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id,@RequestBody User newUser){
       return  service.updateUser(id,newUser);
    }
}
