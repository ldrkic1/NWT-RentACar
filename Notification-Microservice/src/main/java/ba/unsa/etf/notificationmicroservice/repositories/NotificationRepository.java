package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n from Notification n order by n.createdAt desc")
    List<Notification> getAllNotifications();

    @Query("SELECT n, 0 AS clazz_ FROM Notification n, User u WHERE :id=u.id AND n.user.id=u.id order by n.createdAt desc")
    List<Notification> findAllByUserId(@Param("id")  Long id);

    //izmedju dva dateTimea
    @Query("SELECT n, 0 AS clazz_ FROM Notification n WHERE n.createdAt >=:localDateTime1 AND n.createdAt<=:localDateTime2")
    List<Notification> getAllBetwennTwoDates(@Param("localDateTime1")  LocalDateTime localDateTime1, @Param("localDateTime2")  LocalDateTime localDateTime2);


}
