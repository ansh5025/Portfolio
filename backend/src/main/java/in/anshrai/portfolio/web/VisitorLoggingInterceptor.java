package in.anshrai.portfolio.web;

import in.anshrai.portfolio.service.VisitorLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

@Component
public class VisitorLoggingInterceptor implements HandlerInterceptor {

    private final VisitorLogService visitorLogService;

    public VisitorLoggingInterceptor(VisitorLogService visitorLogService) {
        this.visitorLogService = visitorLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        visitorLogService.logVisit(
                resolveClientIp(request),
                detectOsType(request.getHeader("User-Agent")),
                detectDeviceType(request.getHeader("User-Agent")),
                request.getMethod(),
                request.getRequestURI()
        );
        return true;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }

        return request.getRemoteAddr();
    }

    private String detectOsType(String userAgent) {
        String normalized = normalize(userAgent);

        if (normalized.contains("windows")) {
            return "Windows";
        }
        if (normalized.contains("android")) {
            return "Android";
        }
        if (normalized.contains("iphone") || normalized.contains("ipad") || normalized.contains("ios")) {
            return "iOS";
        }
        if (normalized.contains("mac os") || normalized.contains("macintosh")) {
            return "macOS";
        }
        if (normalized.contains("linux")) {
            return "Linux";
        }

        return "Unknown";
    }

    private String detectDeviceType(String userAgent) {
        String normalized = normalize(userAgent);

        if (normalized.contains("tablet") || normalized.contains("ipad")) {
            return "Tablet";
        }
        if (normalized.contains("mobile") || normalized.contains("iphone") || normalized.contains("android")) {
            return "Mobile";
        }

        return "Desktop";
    }

    private String normalize(String userAgent) {
        return userAgent == null ? "" : userAgent.toLowerCase(Locale.ROOT);
    }
}
