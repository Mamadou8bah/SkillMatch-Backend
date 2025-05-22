package SkillMatch.service;

import SkillMatch.dto.EmployerDTO;
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

    public List<EmployerDTO> getEmployers(){
       List<Employer>employers=repo.findAll();
       List <EmployerDTO> employerDTOS=new ArrayList<>();
       for (Employer employer:employers){
           employerDTOS.add(new EmployerDTO(employer.getCompanyName(), employer.getWebsite(), employer.getIndustry()));
       }
       return employerDTOS;
    }

    public Employer getEmployerById(Long id){
        return repo.findById(id).orElseThrow(()->new RuntimeException("Candidate Not Found"));
    }

    public Employer addCandidate(Employer employer){
        return repo.save(employer);
    }

    public void DeleteEmployer(Long id){
       repo.deleteById(id);
    }
    public Employer UpdateEmployer(Long id,Employer employer){

        Employer currentData=repo.findById(id).orElseThrow(()->new RuntimeException("Employer Not Found"));
        currentData.setCompanyName(employer.getCompanyName());
        currentData.setDescription(employer.getDescription());
        currentData.setIndustry(employer.getIndustry());
        return currentData;

    }
}
