package ba.unsa.etf.vehiclemicroservice.demo.Repository;

import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
public interface CarShareRepository extends JpaRepository<CarShare, Long> {
    @Transactional
    @Modifying
    @Query("delete from CarShare c where c.reservation.id =:reservationId")
    void deleteCareShareReservations(Long reservationId);
}
