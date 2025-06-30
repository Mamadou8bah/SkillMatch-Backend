package SkillMatch.controller;

import SkillMatch.dto.ExperienceDTO;
import SkillMatch.model.Experience;
import SkillMatch.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    @Autowired
    ExperienceService service;

    @GetMapping
    public List<ExperienceDTO> getExperiences(){
      return service.getExperiences();
    }

    @GetMapping("/{id}")
    public Experience getExperienceById(@PathVariable long id){
        return service.getExperienceById(id);
    }

    @PostMapping
    public Experience addExperience(@RequestBody Experience experience){
        return service.addExperience(experience);
    }

    @PutMapping("/{id}")
    public Experience updateExperience(@PathVariable long id,@RequestBody Experience newExperience){
       return service.updateExperience(id,newExperience);
    }

    @DeleteMapping("/{id}")
    public Experience deleteExperience(@PathVariable long id){
       return service.deleteExperience(id);
    }

}
