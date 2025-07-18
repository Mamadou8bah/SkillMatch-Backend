package SkillMatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "candidates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 200)
    private String bio;

    @Column(length = 200)
    private String profession;

    private String cvUrl;
    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Skill> skills;


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;


    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Education> educationHistory;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Application> applications;

}
