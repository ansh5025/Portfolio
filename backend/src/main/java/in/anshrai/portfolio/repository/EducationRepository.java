package in.anshrai.portfolio.repository;

import in.anshrai.portfolio.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByOrderByDisplayOrderAsc();
}
