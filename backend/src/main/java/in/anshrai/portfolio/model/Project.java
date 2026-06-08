package in.anshrai.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String githubUrl;
    private String liveUrl;
    private Integer displayOrder;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_tech", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tech")
    @Builder.Default
    private List<String> techStack = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_bullets", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "bullet", length = 1000)
    @Builder.Default
    private List<String> bullets = new ArrayList<>();
}
