package in.anshrai.portfolio.service;

import in.anshrai.portfolio.model.Experience;
import in.anshrai.portfolio.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAll() {
        return experienceRepository.findAllByOrderByDisplayOrderAsc();
    }
}
