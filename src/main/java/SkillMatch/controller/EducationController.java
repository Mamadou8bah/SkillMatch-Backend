package SkillMatch.controller;

import SkillMatch.dto.ApiResponse;
import SkillMatch.dto.EducationDTO;
import SkillMatch.model.Education;
import SkillMatch.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {
    @Autowired
    EducationService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationDTO>>> getEducation(){
        List<EducationDTO> educations = service.getEducation();
        return ResponseEntity.ok(ApiResponse.success("Education records retrieved successfully", educations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Education>> getEducationById(@PathVariable long id){
        Education education = service.getEducation(id);
        return ResponseEntity.ok(ApiResponse.success("Education record retrieved successfully", education));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Education>> addEducation(@Valid @RequestBody Education education){
        Education savedEducation = service.addEducation(education);
        return ResponseEntity.ok(ApiResponse.success("Education record added successfully", savedEducation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Education>> updateEducation(@PathVariable long id, @Valid @RequestBody Education education){
        Education updatedEducation = service.updateEducation(id, education);
        return ResponseEntity.ok(ApiResponse.success("Education record updated successfully", updatedEducation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEducation(@PathVariable long id){
        service.deleteEducation(id);
        return ResponseEntity.ok(ApiResponse.success("Education record deleted successfully"));
    }
}
