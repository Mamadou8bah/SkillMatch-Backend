package SkillMatch.controller;

import SkillMatch.dto.EducationDTO;
import SkillMatch.model.Education;
import SkillMatch.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {
    @Autowired
    EducationService service;

    @GetMapping
    public List<EducationDTO>getEducation(){
        return service.getEducation();
    }

    @GetMapping("/{id}")
    public Education getEducationById(@PathVariable long id){
        return service.getEducation(id);
    }

    @PostMapping
    public Education addEducation(@RequestBody Education education){
        return service.addEducation(education);
    }

    @PutMapping("/{id}")
    public Education updateEducation(@PathVariable long id, @RequestBody Education education){
        return service.updateEducation(id,education);
    }
}
