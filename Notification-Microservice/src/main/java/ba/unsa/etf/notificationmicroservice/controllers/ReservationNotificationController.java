package ba.unsa.etf.notificationmicroservice.controllers;

import ba.unsa.etf.notificationmicroservice.exceptions.ValidationException;
import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.Reservation;
import ba.unsa.etf.notificationmicroservice.models.ReservationNotification;
import ba.unsa.etf.notificationmicroservice.services.QuestionNotificationService;
import ba.unsa.etf.notificationmicroservice.services.ReservationNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservationNotifications")
public class ReservationNotificationController {
    @Autowired
    private ReservationNotificationService service;

    @PostMapping("/newReservationNotification")
    public ReservationNotification addReservationNotification(@RequestBody ReservationNotification reservationNotification, Errors errors){
        return service.addReservationNotification(reservationNotification);
    }
    @GetMapping(path = "/all")
    public List<ReservationNotification> getReservationNotifications() {
        return service.getAllReservationNotifications();
    }
    @GetMapping(path = "/client")
    public List<ReservationNotification> getClientResevationNotifications(@RequestParam(value = "id") Long id) {
        return service.getAllClientReservationNotifications(id); }

    @GetMapping(path = "/reservation")
    public ReservationNotification getReservationNotificationsByQuestion(@RequestParam(value = "id") Long id) {
        return service.getReservationNotificationByReservation(id); }

    @GetMapping(path = "/reservationNotification")
    public ReservationNotification getReservationNotificationById(@RequestParam(value = "id") Long id) {
        return service.getReservationNotificationById(id); }

    @GetMapping(path = "/between")
    public List<ReservationNotification> getNotificationsBetweenTwoDates(@RequestParam(value = "localDateTime1") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime1, @RequestParam(value = "localDateTime2") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime2) {
        return service.getAllReservationNotificationsBetweenTwoDates(localDateTime1, localDateTime2); }
}
