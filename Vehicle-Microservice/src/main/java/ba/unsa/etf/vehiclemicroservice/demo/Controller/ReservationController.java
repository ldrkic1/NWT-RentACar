package ba.unsa.etf.vehiclemicroservice.demo.Controller;

import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Services.ReservationService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService1) { this.reservationService = reservationService1; }
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createNewReservation(reservation);
    }
    @GetMapping(path="/all")
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }
    @GetMapping
    public Reservation getReservationById(@RequestParam(value = "id") Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }
    @GetMapping(path="/count")
     public String numberOfReservationsWithTheSameStart(@RequestParam(value = "reservationStart") LocalDate date) {
        return "Number of reservations that on the same date is: " + reservationService.numberOfReservationsWithTheSameStart(date);
    }
}