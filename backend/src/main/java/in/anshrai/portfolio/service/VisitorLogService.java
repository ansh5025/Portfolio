package in.anshrai.portfolio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VisitorLogService {

    private static final Logger log = LoggerFactory.getLogger(VisitorLogService.class);

    public void logVisit(
            String ipAddress,
            String osType,
            String deviceType,
            String method,
            String path,
            String model,
            Double latitude,
            Double longitude,
            Double accuracy,
            String locationStatus
    ) {
        log.info(
                "Visitor IP={} OS={} Device={} Model={} Latitude={} Longitude={} Accuracy={} LocationStatus={} Method={} Path={}",
                sanitize(ipAddress),
                sanitize(osType),
                sanitize(deviceType),
                sanitize(model),
                sanitizeNumber(latitude),
                sanitizeNumber(longitude),
                sanitizeNumber(accuracy),
                sanitize(locationStatus),
                sanitize(method),
                sanitize(path)
        );
    }

    private String sanitize(String value) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String sanitizeNumber(Double value) {
        return value == null ? "unknown" : value.toString();
    }
}
