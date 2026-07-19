package in.anshrai.portfolio.service;

import in.anshrai.portfolio.dto.ContactRequest;
import in.anshrai.portfolio.model.ContactMessage;
import in.anshrai.portfolio.repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repository;
    private final ResendEmailSender resendEmailSender;

    public ContactService(ContactMessageRepository repository,
                          ResendEmailSender resendEmailSender) {
        this.repository = repository;
        this.resendEmailSender = resendEmailSender;
    }

    @Transactional
    public ContactMessage save(ContactRequest request) {
        ContactMessage entity = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .createdAt(Instant.now())
                .build();

        ContactMessage saved = repository.save(entity);
        try {
            resendEmailSender.send(saved);
        } catch (Exception e) {
            log.error("Failed to send email notification for contact id={}: {}", saved.getId(), e.getMessage());
        }
        log.info("Stored contact message id={} from {}", saved.getId(), saved.getEmail());
        return saved;
    }
}
