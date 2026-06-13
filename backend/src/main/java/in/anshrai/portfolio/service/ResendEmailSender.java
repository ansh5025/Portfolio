package in.anshrai.portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.anshrai.portfolio.model.ContactMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class ResendEmailSender {

    private static final Logger log = LoggerFactory.getLogger(ResendEmailSender.class);
    private static final String RESEND_SEND_EMAIL_URL = "https://api.resend.com/emails";

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String apiKey;
    private final String fromEmail;
    private final String toEmail;

    public ResendEmailSender(ObjectMapper objectMapper,
                             @Value("${app.contact.mail.api-key:}") String apiKey,
                             @Value("${app.contact.mail.from:}") String fromEmail,
                             @Value("${app.contact.mail.to:}") String toEmail) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public void send(ContactMessage message) {
        validateConfiguration();

        HttpRequest request = HttpRequest.newBuilder(URI.create(RESEND_SEND_EMAIL_URL))
                .timeout(Duration.ofSeconds(20))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(buildPayload(message)))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error("Resend send failed status={} body={}", response.statusCode(), response.body());
                throw new IllegalStateException("Unable to send message right now. Please try again later.");
            }
        } catch (IOException | InterruptedException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("Resend request failed for contact message id={}", message.getId(), ex);
            throw new IllegalStateException("Unable to send message right now. Please try again later.", ex);
        }
    }

    private void validateConfiguration() {
        if (isBlank(apiKey) || isBlank(fromEmail) || isBlank(toEmail)) {
            throw new IllegalStateException("Contact email is not configured.");
        }
    }

    private String buildPayload(ContactMessage message) {
        Map<String, Object> payload = Map.of(
                "from", fromEmail,
                "to", List.of(toEmail),
                "reply_to", message.getEmail(),
                "subject", buildSubject(message),
                "text", buildBody(message)
        );

        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Unable to prepare contact email.", ex);
        }
    }

    private String buildSubject(ContactMessage message) {
        String subject = message.getSubject();
        if (isBlank(subject)) {
            return "Portfolio contact from " + message.getName();
        }
        return "[Portfolio] " + subject.trim();
    }

    private String buildBody(ContactMessage message) {
        return """
                New portfolio contact message

                Name: %s
                Email: %s
                Subject: %s
                Time: %s

                Message:
                %s
                """.formatted(
                message.getName(),
                message.getEmail(),
                isBlank(message.getSubject()) ? "(no subject)" : message.getSubject(),
                message.getCreatedAt(),
                message.getMessage()
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
