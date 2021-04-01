package ba.unsa.etf.notificationmicroservice.services;

import ba.unsa.etf.notificationmicroservice.models.QuestionNotification;
import ba.unsa.etf.notificationmicroservice.models.Reservation;
import ba.unsa.etf.notificationmicroservice.models.ReservationNotification;
import ba.unsa.etf.notificationmicroservice.repositories.QuestionRepository;
import ba.unsa.etf.notificationmicroservice.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void save(Reservation reservation1) {
        reservationRepository.save(reservation1);
    }
    public List<ReservationNotification> getReservation(Long id) {
        return reservationRepository.findAllByReservationId(id);
    }
}
