package SkillMatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Photo must have a Url")
    private String url;

    @NotNull(message = "Photo must have a public id")
    private String publicId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
