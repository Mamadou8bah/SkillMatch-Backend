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
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String  companyName;
    private String website;
    @Column(nullable = false)
    private String industry;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String description;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "employer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<JobPost>jobPosts;


}
