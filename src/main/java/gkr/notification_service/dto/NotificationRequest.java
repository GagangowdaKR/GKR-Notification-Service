package gkr.notification_service.dto;

import gkr.notification_service.enums.NotificationType;

public record NotificationRequest(
        NotificationType type,
        String recipient,
        String subject,
        String body
) {
}
