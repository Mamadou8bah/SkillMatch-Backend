package SkillMatch.controller;

import SkillMatch.dto.ApiResponse;
import SkillMatch.dto.ExperienceDTO;
import SkillMatch.model.Experience;
import SkillMatch.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    @Autowired
    ExperienceService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceDTO>>> getExperiences(){
        List<ExperienceDTO> experiences = service.getExperiences();
        return ResponseEntity.ok(ApiResponse.success("Experiences retrieved successfully", experiences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Experience>> getExperienceById(@PathVariable long id){
        Experience experience = service.getExperienceById(id);
        return ResponseEntity.ok(ApiResponse.success("Experience retrieved successfully", experience));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Experience>> addExperience(@Valid @RequestBody Experience experience){
        Experience savedExperience = service.addExperience(experience);
        return ResponseEntity.ok(ApiResponse.success("Experience added successfully", savedExperience));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Experience>> updateExperience(@PathVariable long id, @Valid @RequestBody Experience newExperience){
        Experience updatedExperience = service.updateExperience(id, newExperience);
        return ResponseEntity.ok(ApiResponse.success("Experience updated successfully", updatedExperience));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExperience(@PathVariable long id){
        service.deleteExperience(id);
        return ResponseEntity.ok(ApiResponse.success("Experience deleted successfully"));
    }
}
