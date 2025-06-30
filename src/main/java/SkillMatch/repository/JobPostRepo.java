package SkillMatch.repository;

import SkillMatch.dto.JobPostDTO;
import SkillMatch.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepo extends JpaRepository<JobPost,Long> {
    List<JobPost> findByTitleContainingIgnoreCase(String title);
}
