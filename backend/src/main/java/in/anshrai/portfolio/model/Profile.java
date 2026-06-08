package in.anshrai.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String tagline;

    @Column(length = 2000)
    private String summary;

    private String email;
    private String phone;
    private String location;

    private String linkedinUrl;
    private String githubUrl;
    private String leetcodeUrl;
    private String codolioUrl;
    private String resumeUrl;
    private String avatarUrl;
}
