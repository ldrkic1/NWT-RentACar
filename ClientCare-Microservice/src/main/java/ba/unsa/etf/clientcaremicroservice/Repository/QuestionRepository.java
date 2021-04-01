package ba.unsa.etf.clientcaremicroservice.Repository;

import ba.unsa.etf.clientcaremicroservice.Model.Question;
import ba.unsa.etf.clientcaremicroservice.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q where q.answered = false")
    List<Question> getUnansweredQuestions();

    @Query("select q from Question q where q.answered = true")
    List<Question> getAnsweredQuestions();

    List<Question> findAllByUserId(Long clientID);

    List<Question> findAllByTitle(String title);

    @Transactional
    @Modifying
    @Query("delete from Answer a where a.question.id =:q_id")
    void deleteQuestionsAnswer(Long q_id);
}
