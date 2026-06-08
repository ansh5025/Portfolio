package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.dto.ContactRequest;
import in.anshrai.portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> submit(@Valid @RequestBody ContactRequest request) {
        var saved = contactService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Message received. Thanks for reaching out!", Map.of("id", saved.getId())));
    }
}
