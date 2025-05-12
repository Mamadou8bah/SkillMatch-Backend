package SkillMatch.dto;

public class JobPostDTO {
    private String title;
    private String employerName;

    public JobPostDTO(String title, String employerName) {
        this.title = title;
        this.employerName = employerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
}
