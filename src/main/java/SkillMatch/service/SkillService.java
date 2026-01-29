package SkillMatch.service;

import SkillMatch.dto.SkillDTO;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Skill;
import SkillMatch.model.User;
import SkillMatch.repository.SkillRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SkillService {


    private final SkillRepo repo;
    public Skill addSkill(Skill skill){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        skill.setUser(user);
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

    public Skill updateSkill(long id, Skill newSkill){
        Skill existingSkill = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
        existingSkill.setTitle(newSkill.getTitle());
        return repo.save(existingSkill);
    }

}
