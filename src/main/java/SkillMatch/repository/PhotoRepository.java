package SkillMatch.repository;

import SkillMatch.model.Candidate;
import SkillMatch.model.Employer;
import SkillMatch.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByUrl(String url);
    Photo findByPublicId(String publicId);
    Photo findByCandidate(Candidate candidate);
    Photo findByEmployer(Employer employer);
}
