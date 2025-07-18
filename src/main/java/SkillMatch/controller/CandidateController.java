package SkillMatch.controller;

import SkillMatch.dto.CandidateDTO;
import SkillMatch.model.Candidate;
import SkillMatch.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @GetMapping
    public List<CandidateDTO>getCandidates(@RequestParam(defaultValue ="1")int page,@RequestParam(defaultValue = "10")int size){
        return service.getCandidates(page, size);
    }

    @GetMapping("/{id}")
    public Candidate getCandidateByID( @PathVariable Long id){
        return service.getCandidateByID(id);
    }

    @PostMapping
    public Candidate addCandidate(@RequestBody Candidate candidate){
        return service.addCandidate(candidate);
    }

    @DeleteMapping
    public void deleteCandidate(@RequestBody Long id){
        service.deleteCandidate(id);
    }

    @PutMapping("/{id}")
    public void UpdateCandidate(@PathVariable Long id, @RequestBody Candidate candidate){
        service.UpdateCandidate(id,candidate);
    }


}
