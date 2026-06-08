package in.anshrai.portfolio.service;

import in.anshrai.portfolio.model.Education;
import in.anshrai.portfolio.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAll() {
        return educationRepository.findAllByOrderByDisplayOrderAsc();
    }
}
