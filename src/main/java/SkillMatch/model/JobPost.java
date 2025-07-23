package SkillMatch.model;

import SkillMatch.util.LocationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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

    @Column(nullable = false)
    private LocationType locationType;
    @OneToMany(mappedBy = "jobpost",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Skill> requiredSkills;

    @Column(name = "salary", nullable = false)
    private double salary;

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
    private List<Application> applications;

    @Column(nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime postedAt;


}
