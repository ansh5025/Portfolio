package in.anshrai.portfolio.service;

import in.anshrai.portfolio.model.Skill;
import in.anshrai.portfolio.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Map<String, List<String>> getSkillsGrouped() {
        return skillRepository.findAllByOrderByCategoryAscDisplayOrderAsc().stream()
                .collect(Collectors.groupingBy(
                        Skill::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(Skill::getName, Collectors.toList())
                ));
    }
}
