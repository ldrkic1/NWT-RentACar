package ba.unsa.etf.vehiclemicroservice.demo.Repository;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.vehicle.id =:vehicleId")
    void deleteVehicleReservations(Long vehicleId);
    @Query("SELECT COUNT (reservationStart) FROM Reservation where reservationStart =: reservationStart")
    Long findByReservationStart(LocalDate reservationStart);
   // @Query("SELECT * FROM Reservation r where r.registered.id=:registeredId")
    List<Reservation> getReservationsByRegisteredId(Long registeredId);
    //Long getIdFromUsername(String username);
}
