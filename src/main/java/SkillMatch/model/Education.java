package SkillMatch.model;

import SkillMatch.util.EducationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "education")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String institutionName;
    private String degree;
    private int yearStarted;
    private int yearCompleted;
    @Enumerated(EnumType.STRING)
    private EducationType educationType;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;


}
