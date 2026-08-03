package gkr.notification_service.services;

import gkr.notification_service.dto.NotificationRequest;
import gkr.notification_service.enums.NotificationType;

public interface NotificationService {
    boolean supports(NotificationType type);
    void send(NotificationRequest request);
}
