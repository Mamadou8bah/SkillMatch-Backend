package SkillMatch.dto;

import SkillMatch.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateDTO {
    private String name;
    private String location;
    private List<Skill> skills;


}
