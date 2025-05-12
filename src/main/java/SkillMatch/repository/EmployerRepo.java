package SkillMatch.repository;

import SkillMatch.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepo extends JpaRepository<Employer,Long> {
}
