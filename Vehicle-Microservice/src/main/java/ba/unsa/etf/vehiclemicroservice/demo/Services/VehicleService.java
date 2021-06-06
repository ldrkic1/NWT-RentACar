package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.ApiRequestException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.CarShareRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.ReservationRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarShareRepository carShareRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getAllCategoryVehicles(Long categoryId) {
        return vehicleRepository.findAllByCategoryId(categoryId);
    }

    public Vehicle getVehicleById(Long id) {
        Optional<Vehicle> optional = vehicleRepository.findById(id);

        if(optional.isPresent()) return optional.get();
        else throw new NotFoundException("Vehicle with id: " + id + " doesn't exist.");
    }

    public String deleteVehicleById(Long id) {
        Optional<Vehicle> optional = vehicleRepository.findById(id);
        if(optional.isPresent()) {
            List<Reservation> reservations = vehicleRepository.getReservationsOfVehicel(id);
            if(reservations.isEmpty()) {
                //vozilo nema rezervacija sacuvanih u bazi
                vehicleRepository.deleteById(id);
                return "Vehicle deleted successfully!";
            }
            else {
                //vozilo ima rezervacije u bazi
                boolean reservationsFinished = true;

                for(Reservation reservation: reservations) {
                    if(reservation.getReservationEnd().compareTo(LocalDate.now())>0) {
                        reservationsFinished = false;
                    }
                }
                if (reservationsFinished) {
                    //ukoliko su sve rezervacije zavrsene, prvo brisemo sve rezervacije i careshares
                    //da bi zatim obrisali vozilo
                    for(Reservation reservation: reservations) {
                        carShareRepository.deleteCareShareReservations(reservation.getId());
                    }
                    reservationRepository.deleteVehicleReservations(id);
                    vehicleRepository.deleteById(id);
                    return "Vehicle deleted successfully!";
                }
                else throw new ApiRequestException("Vehicle with id: " + id + " can't be deleted becaouse, it's reserved!");
            }

        }
        else throw new NotFoundException("Vehicle with id: " + id + " doesn't exist.");
    }
    @Transactional
    public Vehicle editVehicle(Vehicle vehicle) {
        Optional<Vehicle> optional = vehicleRepository.findById(vehicle.getId());
        if(optional.isPresent()) {
            Category category = categoryService.findCategoryByDescription(vehicle.getCategory().getDescription());
            if(!vehicle.getModel().isBlank()) optional.get().setModel(vehicle.getModel());
            else throw new ValidationException("Model is required");
            if(vehicle.getBrojSjedista()>1)optional.get().setBrojSjedista(vehicle.getBrojSjedista());
            else throw new ValidationException("Number of seats can't be less than 2!");
            optional.get().setPotrosnja(vehicle.getPotrosnja());
            optional.get().setCategory(category);
            optional.get().setURL(vehicle.getURL());
            return optional.get();
        }
        else throw new NotFoundException("Vehicle with id: " + vehicle.getId() + " doesn't exist.");
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        Category category = categoryService.findCategoryByDescription(vehicle.getCategory().getDescription());
        System.out.println("OVO JE BANANA");
        vehicle.setCategory(category);
        if(vehicle.getModel() == null || vehicle.getModel().isBlank()) throw new ValidationException("Model is required");
        if(vehicle.getBrojSjedista()<2) throw new ValidationException("Number of seats can't be less than 2!");
        try {
            return vehicleRepository.save(vehicle);
        }catch (Exception e){
            throw new ValidationException("The category is wrong, try again.");
        }
    }
}
