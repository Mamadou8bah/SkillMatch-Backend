package SkillMatch.service;
import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Candidate;
import SkillMatch.repository.CandidateRepo;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepo repo;

    @Transactional
    public List<Candidate>getCandidates(int page,int size){
        Pageable pageable= PageRequest.of(page, size);

        Page<Candidate>pages=repo.findAll(pageable);
        List<Candidate> candidates=pages.getContent();
        return candidates;
    }

     @Transactional
    public Candidate getCandidateByID(Long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Candidate not found"));
    }
     @Transactional
    public Candidate addCandidate(Candidate candidate){
        return repo.save(candidate);
    }
     @Transactional
    public void deleteCandidate(Long id){
        repo.deleteById(id);
    }
     @Transactional
    public Candidate UpdateCandidate(Long id,Candidate candidate){
        Candidate update=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Candidate not found"));
        update.setUser(candidate.getUser());
        update.setBio(candidate.getBio());
        update.setExperiences(candidate.getExperiences());
        update.setEducationHistory(candidate.getEducationHistory());
        update.setSkills(candidate.getSkills());
        return repo.save(update);
    }

}
