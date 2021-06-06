package ba.unsa.etf.vehiclemicroservice.demo.Controller;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Registered;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Services.ReservationService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService1) { this.reservationService = reservationService1; }
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) throws JSONException {
        System.out.println(reservation.getVehicle().getModel());
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
    @GetMapping(path="/registered")
    public List<Reservation> getReservationById1(@RequestParam(value = "id") Long reservationId) {
        System.out.println("OVO JE ID "+ reservationId);
        return reservationService.getReservationByRegisteredId(reservationId);
    }
    @GetMapping(path="/username")
    public Optional<Registered> getIdFromUsername(@RequestParam(value = "username") String description) {
        System.out.println("USAO SAM U KONTROLER");
        return reservationService.getRegistered(description);
    }
    @DeleteMapping
    public String deleteVehicleById(@RequestParam(value="id") Long id) {
        return reservationService.deleteVehicleById(id);
    }
}