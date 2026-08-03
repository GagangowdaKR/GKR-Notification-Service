package gkr.notification_service.controllers;

import gkr.notification_service.dto.NotificationRequest;
import gkr.notification_service.services.NotificationRouterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRouterService routerService;

    public NotificationController(NotificationRouterService routerService) {
        this.routerService = routerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            routerService.dispatch(request);
            return ResponseEntity.ok("Notification dispatched successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to dispatch notification: " + e.getMessage());
        }
    }
}
