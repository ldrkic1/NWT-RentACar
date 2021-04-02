package ba.unsa.etf.vehiclemicroservice.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
@Entity
@Table
public class CarShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int NumberOfFreeSpaces;
    @OneToOne
    @JoinColumn(name = "reservation_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Reservation reservation;

    public CarShare() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfFreeSpaces() {
        return NumberOfFreeSpaces;
    }

    public void setNumberOfFreeSpaces(int numberOfFreeSpaces) {
        NumberOfFreeSpaces = numberOfFreeSpaces;
    }

  public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
