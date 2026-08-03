package gkr.notification_service.controllers;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/app")
public class AppController {

    private BuildProperties buildProperties;

    public AppController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getAppDetails() {
        Map<String, Object> details = Map.of(
                "name", buildProperties.getName(),
                "version", buildProperties.getVersion(),
                "time", buildProperties.getTime(),
                "status", "UP"
        );
        return ResponseEntity.ok(details);
    }
}