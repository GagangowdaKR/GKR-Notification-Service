package gkr.notification_service.services;

import gkr.notification_service.dto.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationRouterService {

    private final List<NotificationService> channels;

    public NotificationRouterService(List<NotificationService> channels) {
        this.channels = channels;
    }

    public void dispatch(NotificationRequest request) {
        NotificationService channel = channels.stream()
                .filter(c -> c.supports(request.type()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported notification type: " + request.type()));

        channel.send(request);
    }
}
