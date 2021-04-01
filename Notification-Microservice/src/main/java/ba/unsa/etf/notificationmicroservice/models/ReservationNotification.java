package ba.unsa.etf.notificationmicroservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class ReservationNotification extends Notification{

    /*@ManyToOne
    @JoinColumn(name="reservation_id", nullable=false)
    private Reservation reservation;*/
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    public ReservationNotification() {
    }

    public ReservationNotification(Reservation reservation) {
        this.reservation = reservation;
    }

    public ReservationNotification(String title, String content, LocalDateTime createdAt, User user, Reservation reservation) {
        super(title, content, createdAt, user);
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
