package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.RoleName;
import ba.unsa.etf.notificationmicroservice.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface QuestionNotificationRepository extends NotificationRepository{

    @Query("SELECT qn from QuestionNotification qn order by qn.createdAt desc")
    List<QuestionNotification> getAllQuestionNotifications();


    @Query("SELECT qn from QuestionNotification qn WHERE qn.id=:id")
    Optional<QuestionNotification> findQuestionNotificationById(@Param("id") Long id);

}
