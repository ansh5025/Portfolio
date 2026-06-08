package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ApiResponse<Map<String, List<String>>> getSkills() {
        return ApiResponse.ok(skillService.getSkillsGrouped());
    }
}
