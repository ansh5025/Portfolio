package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    @PostMapping("/landing")
    public ApiResponse<Void> trackLandingVisit() {
        return ApiResponse.ok("Landing visit logged", null);
    }
}
