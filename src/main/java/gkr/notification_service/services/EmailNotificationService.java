package gkr.notification_service.services;

import gkr.notification_service.dto.NotificationRequest;
import gkr.notification_service.enums.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EmailNotificationService implements NotificationService {

    private final RestClient restClient;

    @Value("${resend.api.key:null}")
    private String apiKey;

    @Value("${resend.sender.email:onboarding@resend.dev}")
    private String senderEmail;

    public EmailNotificationService() {
        this.restClient = RestClient.builder().build();
    }

    @Override
    public boolean supports(NotificationType type) {
        return NotificationType.EMAIL.equals(type);
    }

    @Override
    public void send(NotificationRequest request) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("from", "GKR Portfolio <" + senderEmail + ">");
        payload.put("to", request.recipient());
        payload.put("subject", request.subject());
        payload.put("html", request.body());

        restClient.post()
                .uri("https://api.resend.com/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();

        log.info("Email sent to " + request.recipient());
    }
}
