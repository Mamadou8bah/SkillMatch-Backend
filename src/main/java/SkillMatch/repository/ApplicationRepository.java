package SkillMatch.repository;

import SkillMatch.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Optional<Application> findByCandidate_IdAndJobPost_Id(Long candidateId, Long jobId);

}
