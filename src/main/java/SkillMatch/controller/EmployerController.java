package SkillMatch.controller;

import SkillMatch.model.Employer;
import SkillMatch.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    public EmployerService service;

    @GetMapping
    public List<Employer>getEmployers(){
        return service.getEmployers();
    }

    @GetMapping("/employer/{id}")
    public Employer getEmployerById( @PathVariable long id){
        return service.getEmployerById(id);
    }
    @PostMapping
    public Employer addEmployer(@RequestBody Employer employer){
        return service.addEmployer(employer);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable long id){
        service.deleteEmployer(id);
    }

    @PutMapping("/{id}")
    public void updateEmployer(@PathVariable long id,@RequestBody Employer employer){
        service.UpdateEmployer(id,employer);
    }
}
