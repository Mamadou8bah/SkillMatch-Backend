package SkillMatch.controller;

import SkillMatch.dto.SkillDTO;
import SkillMatch.model.Skill;
import SkillMatch.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {


    @Autowired
    SkillService service;

    @PostMapping
    public Skill addSkill(@RequestBody Skill skill){
        return service.addSkill(skill);
    }

    @GetMapping
    public List<SkillDTO> getSkills(){
        return service.getSkills();
    }

    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable long id){
       return service.getSkillById(id);
    }

    @DeleteMapping("/{id}")
    public Skill deleteSkill(@PathVariable long id){
      return service.deleteSkill(id);
    }

}
