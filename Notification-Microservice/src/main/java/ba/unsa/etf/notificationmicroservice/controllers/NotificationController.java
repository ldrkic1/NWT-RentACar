package ba.unsa.etf.notificationmicroservice.controllers;

import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping(path = "/all")
    public List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping(path = "/client")
    public List<Notification> getClientNotifications(@RequestParam(value = "id") Long id) {
        return notificationService.getAllNotificationsByClient(id); }

    @GetMapping(path = "/notification")
    public Notification getNotificationById(@RequestParam(value = "id") Long id) {
        return notificationService.getNotificationById(id); }

    @GetMapping(path = "/between")
    public List<Notification> getNotificationsBetweenTwoDates(@RequestParam(value = "localDateTime1") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime1, @RequestParam(value = "localDateTime2") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime2) {
        return notificationService.getAllNotificationsBetweenTwoDates(localDateTime1, localDateTime2); }
}
