package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.dto.VisitorLogEntry;
import in.anshrai.portfolio.dto.VisitorRequest;
import in.anshrai.portfolio.service.VisitorLogStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitorLogStorageService visitorLogStorageService;

    public VisitController(VisitorLogStorageService visitorLogStorageService) {
        this.visitorLogStorageService = visitorLogStorageService;
    }

    @PostMapping("/landing")
    public ApiResponse<Void> trackLandingVisit(@RequestBody(required = false) VisitorRequest request,
                                               HttpServletRequest httpRequest) {
        if (request != null) {
            httpRequest.setAttribute("visitorModel", request.getModel());
            httpRequest.setAttribute("visitorLatitude", request.getLatitude());
            httpRequest.setAttribute("visitorLongitude", request.getLongitude());
            httpRequest.setAttribute("visitorAccuracy", request.getAccuracy());
            httpRequest.setAttribute("visitorLocationStatus", request.getLocationStatus());
        }
        return ApiResponse.ok("Landing visit logged", null);
    }

    @GetMapping("/landing/logs")
    public ApiResponse<List<VisitorLogEntry>> getLandingLogs() {
        return ApiResponse.ok(visitorLogStorageService.getAll());
    }
}
