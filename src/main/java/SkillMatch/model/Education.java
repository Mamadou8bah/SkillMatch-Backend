package SkillMatch.model;

import SkillMatch.util.EducationType;
import jakarta.persistence.*;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(int yearStarted) {
        this.yearStarted = yearStarted;
    }

    public int getYearCompleted() {
        return yearCompleted;
    }

    public void setYearCompleted(int yearCompleted) {
        this.yearCompleted = yearCompleted;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
