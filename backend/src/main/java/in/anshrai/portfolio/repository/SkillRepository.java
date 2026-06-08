package in.anshrai.portfolio.repository;

import in.anshrai.portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByOrderByCategoryAscDisplayOrderAsc();
}
