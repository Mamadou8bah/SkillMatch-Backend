package SkillMatch.dto;

import SkillMatch.model.Skill;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CandidateDTO {
    private String name;
    private String location;
    private List<Skill> skills;


}
