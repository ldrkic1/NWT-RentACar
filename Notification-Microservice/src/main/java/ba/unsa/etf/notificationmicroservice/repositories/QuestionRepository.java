package ba.unsa.etf.notificationmicroservice.repositories;

import ba.unsa.etf.notificationmicroservice.models.Notification;
import ba.unsa.etf.notificationmicroservice.models.Question;
import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q.questionNotification FROM Question q WHERE q.id = :id")
    List<QuestionNotification> findAllByQuestionId(@Param("id") Long id);

}
