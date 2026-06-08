package in.anshrai.portfolio.service;

import in.anshrai.portfolio.model.Project;
import in.anshrai.portfolio.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll() {
        return projectRepository.findAllByOrderByDisplayOrderAsc();
    }
}
