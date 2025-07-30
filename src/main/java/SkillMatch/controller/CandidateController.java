package SkillMatch.controller;


import SkillMatch.model.Candidate;
import SkillMatch.service.CandidateService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @GetMapping
    public ResponseEntity<?> getCandidates(@RequestParam(defaultValue ="1")int page, @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(service.getCandidates(page, size)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCandidateByID( @PathVariable Long id){
        Candidate candidate= service.getCandidateByID(id);
        return ResponseEntity.status(200).body(candidate);
    }

    @PostMapping
    public ResponseEntity<?> addCandidate(@Valid @RequestBody Candidate candidate){
        return ResponseEntity.status(201).body(service.addCandidate(candidate));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCandidate(@RequestBody Long id){
        service.deleteCandidate(id);
        return ResponseEntity.ok("Candidate Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateCandidate(@PathVariable Long id, @RequestBody Candidate candidate){
        service.UpdateCandidate(id,candidate);
        return ResponseEntity.ok("Candidate updated");
    }


}
