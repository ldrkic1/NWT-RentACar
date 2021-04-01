package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.models.Reservation;
import ba.unsa.etf.notificationmicroservice.models.ReservationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r.reservationNotification FROM Reservation r WHERE r.id = :id")
    List<ReservationNotification> findAllByReservationId(@Param("id") Long id);
}
