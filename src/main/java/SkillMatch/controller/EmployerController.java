package SkillMatch.controller;

import SkillMatch.model.Employer;
import SkillMatch.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    public EmployerService service;

    @GetMapping
    public ResponseEntity<?>getEmployers(){
        return ResponseEntity.ok(service.getEmployers());
    }

    @GetMapping("/employer/{id}")
    public ResponseEntity<?> getEmployerById( @PathVariable long id){
        return ResponseEntity.ok(service.getEmployerById(id));
    }
    @PostMapping
    public ResponseEntity<?> addEmployer(@RequestBody Employer employer){
        return ResponseEntity.ok(service.addEmployer(employer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable long id){
        service.deleteEmployer(id);
        return ResponseEntity.ok("Profile Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployer(@PathVariable long id,@RequestBody Employer employer){
        service.UpdateEmployer(id,employer);
        return ResponseEntity.ok("Profile Updated");
    }
}
