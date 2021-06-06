package ba.unsa.etf.vehiclemicroservice.demo.Repository;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Optional<Vehicle> findByModel(String model);
    List<Vehicle> findAllByCategoryId(Long categoryId);
    @Query("select r from  Reservation  r, Vehicle  v where  r.vehicle.id = v.id and v.id=:vehicleId")
    List<Reservation> getReservationsOfVehicel(Long vehicleId);
}
