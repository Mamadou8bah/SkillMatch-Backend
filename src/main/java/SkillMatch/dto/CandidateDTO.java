package SkillMatch.dto;

import SkillMatch.model.Skill;

import java.util.List;

public class CandidateDTO {
    private String name;
    private String location;
    private List<Skill> skills;

    public CandidateDTO(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public CandidateDTO(String name, String location, List<Skill> skills) {
        this.name = name;
        this.location = location;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
