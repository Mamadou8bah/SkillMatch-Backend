package SkillMatch.repository;

import SkillMatch.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepo extends JpaRepository<JobPost,Long> {
}
