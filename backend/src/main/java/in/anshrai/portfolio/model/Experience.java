package in.anshrai.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String role;
    private String location;
    private String period;
    private boolean current;
    private Integer displayOrder;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "experience_bullets", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "bullet", length = 1000)
    @Builder.Default
    private List<String> bullets = new ArrayList<>();
}
