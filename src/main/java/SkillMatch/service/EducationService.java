package SkillMatch.service;

import SkillMatch.dto.EducationDTO;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Education;
import SkillMatch.repository.EducationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationService {

    @Autowired
    EducationRepo repo;
    public List<EducationDTO>getEducation(){
        List<Education>educations=repo.findAll();
        List<EducationDTO>educationDTOList=new ArrayList<>();

        for(Education education:educations){
            educationDTOList.add(new EducationDTO(education.getDegree(),education.getEducationType()));
        }
        return educationDTOList;
    }

    public Education getEducation(long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Education not found"));
    }

    public Education addEducation(Education education){
        return repo.save(education);
    }
    public Education updateEducation(long id,Education education){
        Education education1=repo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Education not found"));
        education1.setEducationType(education.getEducationType());
        education1.setCandidate(education.getCandidate());
        education1.setDegree(education.getDegree());
        education1.setYearCompleted(education.getYearCompleted());
        education1.setYearStarted(education.getYearStarted());
        education1.setInstitutionName(education.getInstitutionName());

        return repo.save(education1);

    }

    public Education deleteEducation(long id){
       Education education=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Education not found"));
       repo.delete(education);
       return education;
    }
}
