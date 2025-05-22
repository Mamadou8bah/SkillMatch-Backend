package SkillMatch.controller;

import SkillMatch.dto.EmployerDTO;
import SkillMatch.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    public EmployerService service;

    @GetMapping
    public List<EmployerDTO>getEmployers(){
        return service.getEmployers();
    }
}
