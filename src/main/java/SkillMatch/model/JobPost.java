package SkillMatch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @OneToMany(mappedBy = "jobpost",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Skill> requiredSkills;
}
