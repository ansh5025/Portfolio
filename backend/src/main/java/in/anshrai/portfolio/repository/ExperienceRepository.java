package in.anshrai.portfolio.repository;

import in.anshrai.portfolio.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByOrderByDisplayOrderAsc();
}
