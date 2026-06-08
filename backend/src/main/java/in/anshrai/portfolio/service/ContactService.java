package in.anshrai.portfolio.service;

import in.anshrai.portfolio.dto.ContactRequest;
import in.anshrai.portfolio.model.ContactMessage;
import in.anshrai.portfolio.repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repository;

    public ContactService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    public ContactMessage save(ContactRequest request) {
        ContactMessage entity = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .createdAt(Instant.now())
                .build();
        ContactMessage saved = repository.save(entity);
        log.info("Stored contact message id={} from {}", saved.getId(), saved.getEmail());
        return saved;
    }
}
