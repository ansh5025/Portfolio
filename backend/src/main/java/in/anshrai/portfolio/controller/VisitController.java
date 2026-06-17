package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.dto.VisitorLogEntry;
import in.anshrai.portfolio.dto.VisitorRequest;
import in.anshrai.portfolio.service.VisitorLogStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
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
    public ResponseEntity<Object> downloadLandingLogs(
            @RequestHeader(value = LOG_SECRET_HEADER, required = false) String secret) {
        if (!isAuthorized(secret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Path logPath = visitorLogStorageService.getLogFilePath();
        if (!Files.exists(logPath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log file not found");
        }

        try {
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(logPath));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=landing-visits.json")
                    .body(resource);
        } catch (IOException ex) {
            Logger logger = LoggerFactory.getLogger(VisitController.class);
            logger.error("Unable to read landing visits log file", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to read log file");
        }
    }

    private boolean isAuthorized(String secret) {
        return visitorLogSecret != null
                && !visitorLogSecret.isBlank()
                && visitorLogSecret.equals(secret);
    }
}
