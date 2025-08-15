package SkillMatch.service;

import SkillMatch.dto.ExperienceDTO;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Experience;
import SkillMatch.repository.ExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExperienceService {
    @Autowired
    ExperienceRepo repo;

    public List<ExperienceDTO> getExperiences(){
        List<Experience>experienceList=repo.findAll();

        List<ExperienceDTO>experienceDTOS=new ArrayList<>();

        for (Experience experience:experienceList){
            experienceDTOS.add(new ExperienceDTO(experience.getCompanyName(),experience.getJobTitle(),experience.getStartDate(),experience.getEndDate()));
        }
        return experienceDTOS;
    }

    public Experience addExperience(Experience experience){
        return repo.save(experience);
    }

    public Experience getExperienceById(long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Experience not found"));
    }   
    public Experience deleteExperience(long id){
        Experience experience=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Experience not found"));
        repo.deleteById(id);
        return experience;
    }
    public Experience updateExperience(long id, Experience newExperience){
        Experience experience=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Experience not found"));
        experience.setCandidate(newExperience.getCandidate());
        experience.setDescription(newExperience.getDescription());
        experience.setCompanyName(newExperience.getCompanyName());
        experience.setJobTitle(newExperience.getJobTitle());
        experience.setStartDate(newExperience.getStartDate());
        experience.setEndDate(newExperience.getEndDate());

        return repo.save(experience);
    }
}
