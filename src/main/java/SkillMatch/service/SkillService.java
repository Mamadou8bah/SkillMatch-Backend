package SkillMatch.service;

import SkillMatch.dto.SkillDTO;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Skill;
import SkillMatch.repository.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SkillService {

    @Autowired
    SkillRepo repo;
    public Skill addSkill(Skill skill){
        return repo.save(skill);
    }

    public List<SkillDTO>getSkills(){
        List<Skill>getSkills= repo.findAll();
        List<SkillDTO>skillDTOS=new ArrayList<>();

        for (Skill skill:getSkills){
            skillDTOS.add(new SkillDTO(skill.getTitle()));
        }
        return skillDTOS;
    }

    public Skill getSkillById(long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
    }
    public Skill deleteSkill(long id){
        Skill skill= repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
        repo.delete(skill);
        return skill;
    }

}
