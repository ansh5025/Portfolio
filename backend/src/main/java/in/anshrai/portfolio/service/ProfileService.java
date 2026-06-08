package in.anshrai.portfolio.service;

import in.anshrai.portfolio.model.Profile;
import in.anshrai.portfolio.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile() {
        return profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Profile not initialized"));
    }
}
