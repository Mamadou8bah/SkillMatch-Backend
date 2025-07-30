package SkillMatch.controller;

import SkillMatch.model.Application;
import SkillMatch.service.ApplicationService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apply")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @PostMapping
    public ResponseEntity<Application> submit(@Valid @RequestBody Application application) {
        Application savedApp = service.submitApplication(application);
        return ResponseEntity.ok(savedApp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        Application app = service.getApplicationById(id);
        return ResponseEntity.ok(app);
    }
}
