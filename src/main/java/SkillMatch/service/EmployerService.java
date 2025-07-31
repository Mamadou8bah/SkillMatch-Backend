package SkillMatch.service;

import SkillMatch.dto.EmployerDTO;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Candidate;
import SkillMatch.model.Employer;
import SkillMatch.repository.EmployerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepo repo;

    public List<Employer> getEmployers(){
       List<Employer>employers=repo.findAll();
       return employers;
    }

    public Employer getEmployerById(Long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employer not found"));
    }

    public Employer addEmployer(Employer employer){
        return repo.save(employer);
    }

    public void deleteEmployer(Long id){
       repo.deleteById(id);
    }
    public Employer UpdateEmployer(Long id,Employer employer){

        Employer currentData=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employer not found"));
        currentData.setCompanyName(employer.getCompanyName());
        currentData.setDescription(employer.getDescription());
        currentData.setIndustry(employer.getIndustry());
        return currentData;

    }
}
