package in.anshrai.portfolio.service;

import in.anshrai.portfolio.dto.ContactRequest;
import in.anshrai.portfolio.model.ContactMessage;
import in.anshrai.portfolio.repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repository;
    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String toEmail;

    public ContactService(ContactMessageRepository repository,
                          JavaMailSender mailSender,
                          @Value("${app.contact.mail.from:}") String fromEmail,
                          @Value("${app.contact.mail.to:}") String toEmail) {
        this.repository = repository;
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    @Transactional
    public ContactMessage save(ContactRequest request) {
        validateMailConfiguration();

        ContactMessage entity = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .createdAt(Instant.now())
                .build();

        ContactMessage saved = repository.save(entity);
        sendNotificationEmail(saved);
        log.info("Stored contact message id={} from {}", saved.getId(), saved.getEmail());
        return saved;
    }

    private void validateMailConfiguration() {
        if (fromEmail == null || fromEmail.isBlank() || toEmail == null || toEmail.isBlank()) {
            throw new IllegalStateException("Contact email is not configured.");
        }
    }

    private void sendNotificationEmail(ContactMessage message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(fromEmail);
        mail.setTo(toEmail);
        mail.setReplyTo(message.getEmail());
        mail.setSubject(buildSubject(message));
        mail.setText(buildBody(message));

        try {
            mailSender.send(mail);
        } catch (MailException ex) {
            log.error("Failed to send contact email for message id={}", message.getId(), ex);
            throw ex;
        }
    }

    private String buildSubject(ContactMessage message) {
        String subject = message.getSubject();
        if (subject == null || subject.isBlank()) {
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
                message.getSubject() == null || message.getSubject().isBlank() ? "(no subject)" : message.getSubject(),
                message.getCreatedAt(),
                message.getMessage()
        );
    }
}
