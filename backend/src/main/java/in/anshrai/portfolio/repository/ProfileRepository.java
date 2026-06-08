package in.anshrai.portfolio.repository;

import in.anshrai.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
