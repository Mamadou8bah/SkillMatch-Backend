package SkillMatch.repository;

import SkillMatch.model.Application;
import SkillMatch.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Optional<Application> findByCandidateIdAndJobId(Long candidateId, Long jobId);

}
