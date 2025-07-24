package SkillMatch.repository;

import SkillMatch.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepo extends JpaRepository<Token,Long> {
    Token findByToken(String jwt);
}
