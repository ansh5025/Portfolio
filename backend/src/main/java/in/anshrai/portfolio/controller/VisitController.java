package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.dto.VisitorLogEntry;
import in.anshrai.portfolio.dto.VisitorRequest;
import in.anshrai.portfolio.service.VisitorLogStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private static final String LOG_SECRET_HEADER = "X-Visitor-Log-Secret";

    private final VisitorLogStorageService visitorLogStorageService;
    private final String visitorLogSecret;

    public VisitController(VisitorLogStorageService visitorLogStorageService,
                           @Value("${app.visitor-log.access-key:}") String visitorLogSecret) {
        this.visitorLogStorageService = visitorLogStorageService;
        this.visitorLogSecret = visitorLogSecret;
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

    @GetMapping("/landing/logs/download")
    public ResponseEntity<Resource> downloadLandingLogs(
            @RequestHeader(value = LOG_SECRET_HEADER, required = false) String secret) {
        if (!isAuthorized(secret)) {
            return ResponseEntity.status(401).build();
        }

        Path logPath = visitorLogStorageService.getLogFilePath();
        if (!logPath.toFile().exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new PathResource(logPath);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=landing-visits.json")
                .body(resource);
    }

    private boolean isAuthorized(String secret) {
        return visitorLogSecret != null
                && !visitorLogSecret.isBlank()
                && visitorLogSecret.equals(secret);
    }
}
