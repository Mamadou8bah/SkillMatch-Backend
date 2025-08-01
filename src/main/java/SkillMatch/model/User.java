package SkillMatch.model;

import SkillMatch.util.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length = 100)
    private String fullName;

    @Column(nullable = false)
    private String profilePicture;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Candidate candidate;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Employer employer;

    @OneToMany(mappedBy = "user")
    private List<Token>tokens;

    @OneToMany(mappedBy = "user")
    private List<SecureToken>secureTokens;

    private boolean accountVerified;

    private boolean loginDisabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }


}
