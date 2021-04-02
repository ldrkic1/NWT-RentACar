package ba.unsa.etf.vehiclemicroservice.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int BrojRezervacije;
    private LocalDate reservationStart;
    private LocalDate reservationEnd;
    private boolean isCarShare;
    /*@OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CarShare carShare;*/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registerd_id", nullable =false)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Registered registered;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable =false)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Vehicle vehicle;


    public Reservation() {
    }

    public boolean isCarShare() {
        return isCarShare;
    }

    public void setCarShare(boolean carShare) {
        isCarShare = carShare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBrojRezervacije() {
        return BrojRezervacije;
    }

    public void setBrojRezervacije(int brojRezervacije) {
        BrojRezervacije = brojRezervacije;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDate reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDate getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDate reservationEnd) {
        this.reservationEnd = reservationEnd;
    }
}

