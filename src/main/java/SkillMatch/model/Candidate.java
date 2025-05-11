package SkillMatch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 200)
    private String bio;
    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Skill> skills;
    private String location;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;


    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Education> educationHistory;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Education> getEducationHistory() {
        return educationHistory;
    }

    public void setEducationHistory(List<Education> educationHistory) {
        this.educationHistory = educationHistory;
    }
}
