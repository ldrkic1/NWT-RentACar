package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.*;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.CarShareRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.RegisteredRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.ReservationRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RegisteredRepository registeredRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CarShareRepository carShareRepository;
    public Reservation createNewReservation(Reservation reservation) {
        if(reservation.getRegistered()== null)
            throw new NotFoundException("No registered user");
        if(reservation.getVehicle() == null)
            throw new NotFoundException("No vehicle");
        Optional<Registered> registered = registeredRepository.findByFirstNameAndLastName(reservation.getRegistered().getFirstName(),reservation.getRegistered().getLastName());
        Optional<Vehicle> vehicle = vehicleRepository.findByModel(reservation.getVehicle().getModel());
        if(registered.isPresent()) {
            if (vehicle.isPresent()) {
                if (!reservation.getReservationStart().isEqual(reservation.getReservationEnd())) {
                    reservation.setVehicle(vehicle.get());
                    reservation.setRegistered(registered.get());

                    Reservation r = reservationRepository.save(reservation);
                    if (r.isCarShare()) {
                        CarShare carShare = new CarShare();
                        carShare.setNumberOfFreeSpaces(reservation.getVehicle().getBrojSjedista() - 1);
                        carShare.setReservation(reservation);
                        carShareRepository.save(carShare);
                    }
                    return r;
                } else throw new ValidationException("Ending date can't be the same as the starting date");
            } else throw new NotFoundException("No vehicle");
        }
        else throw new NotFoundException("No registered user");
    }
    public Long numberOfReservationsWithTheSameStart(LocalDate date) {
        return reservationRepository.findByReservationStart(date);

    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isPresent()) {
            return reservation.get();
        }
        else throw new NotFoundException("Reservation with id: " + reservationId + " doesn't exist.");

    }
}