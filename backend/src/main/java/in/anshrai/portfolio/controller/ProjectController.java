package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.model.Project;
import in.anshrai.portfolio.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ApiResponse<List<Project>> getProjects() {
        return ApiResponse.ok(projectService.getAll());
    }
}
