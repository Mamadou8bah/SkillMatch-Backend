package SkillMatch.service;

import SkillMatch.dto.CandidateDTO;
import SkillMatch.model.Candidate;
import SkillMatch.repository.CandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepo repo;

    public List<CandidateDTO>getCandidates(){
        List<Candidate> candidates=repo.findAll();
        List<CandidateDTO> candidateDTOS=new ArrayList<>();
        for (Candidate candidate:candidates){
            candidateDTOS.add(new CandidateDTO(candidate.getFullName(), candidate.getLocation(),candidate.getSkills()));
        }
        return candidateDTOS;
    }

    public Candidate getCandidateByID(Long id){
        return repo.findById(id).orElseThrow(()->new RuntimeException("Candidate Not Found"));
    }
    public Candidate addCandidate(Candidate candidate){
        return repo.save(candidate);
    }
    public void deleteCandidate(Long id){
        repo.deleteById(id);
    }
    public void UpdateCandidate(Long id,Candidate candidate){
        Candidate update=repo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        update.setUser(candidate.getUser());
        update.setBio(candidate.getBio());
        update.setExperiences(candidate.getExperiences());
        update.setEducationHistory(candidate.getEducationHistory());
        update.setSkills(candidate.getSkills());
    }

}
