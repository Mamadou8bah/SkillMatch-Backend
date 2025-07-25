package SkillMatch.repository;

import SkillMatch.model.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long> {

    SecureToken findByToken(String token);
    Long removeByToken(String string);
}
