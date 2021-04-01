package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.Reservation;
import ba.unsa.etf.notificationmicroservice.models.ReservationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationNotificationRepository extends NotificationRepository {

    @Query("SELECT r from ReservationNotification r order by r.createdAt desc")
    List<ReservationNotification> getAllReservationNotifications();

    @Query("SELECT rn from ReservationNotification rn WHERE rn.id=:id")
    Optional<ReservationNotification> findReservationNotificationById(@Param("id") Long id);

}
