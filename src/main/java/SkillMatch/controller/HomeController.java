package SkillMatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller

@RestController("/")
public class HomeController {

    @GetMapping
    public String welcome(){
        return "Welcome to SkillMatch";
    }
}
