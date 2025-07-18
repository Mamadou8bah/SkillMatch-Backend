package SkillMatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name = "salary", nullable = false)
    private double salary;

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
    private List<Application> applications;


}
