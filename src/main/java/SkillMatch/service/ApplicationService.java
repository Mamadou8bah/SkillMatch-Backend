package SkillMatch.service;

import SkillMatch.model.Application;
import SkillMatch.repository.ApplicationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Transactional
    public Application submitApplication(Application application) {
        Long candidateId = application.getCandidate().getId();
        Long jobId = application.getJobPost().getId();

        Optional<Application> existingApp = repository.findByCandidate_IdAndJobPost_Id(candidateId, jobId);

        if (existingApp.isPresent()) {
            throw new RuntimeException("You have already applied to this job.");
        }

        return repository.save(application);
    }

    public void deleteApplication(Long applicationId) {
        Application app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        repository.delete(app);
    }

    public Application getApplicationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }






}
