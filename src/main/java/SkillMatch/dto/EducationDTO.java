package SkillMatch.dto;

import SkillMatch.util.EducationType;

public class EducationDTO {
    private String degree;
    private EducationType educationType;

    public EducationDTO(String degree, EducationType educationType) {
        this.degree = degree;
        this.educationType = educationType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }
}
