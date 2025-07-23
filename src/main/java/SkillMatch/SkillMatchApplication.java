package SkillMatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkillMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillMatchApplication.class, args);
	}

}
