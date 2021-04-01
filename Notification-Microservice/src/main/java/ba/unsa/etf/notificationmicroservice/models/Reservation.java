package ba.unsa.etf.notificationmicroservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /*@OneToMany(mappedBy="reservation")
    private Set<ReservationNotification> reservationNotifications;*/
    @JsonBackReference
    @OneToOne(mappedBy = "reservation")
    private ReservationNotification reservationNotification;

    public Reservation() {
        reservationNotification = new ReservationNotification();
    }
    public Reservation(ReservationNotification reservationNotification) {
        this.reservationNotification = reservationNotification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationNotification getReservationNotification() {
        return reservationNotification;
    }

    public void setReservationNotification(ReservationNotification reservationNotification) {
        this.reservationNotification = reservationNotification;
    }
}
