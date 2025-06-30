package SkillMatch.dto;

import SkillMatch.model.Employer;

public class JobPostDTO {
    private String title;
    private Employer employerName;

    public JobPostDTO(String title, Employer employerName) {
        this.title = title;
        this.employerName = employerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Employer getEmployerName() {
        return employerName;
    }

    public void setEmployerName(Employer employerName) {
        this.employerName = employerName;
    }
}
