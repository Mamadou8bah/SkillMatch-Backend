package SkillMatch.repository;

import SkillMatch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
