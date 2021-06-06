package ba.unsa.etf.vehiclemicroservice.demo.Controller;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;
import ba.unsa.etf.vehiclemicroservice.demo.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    //http://localhost:8080/vehicle/all
    @GetMapping(path = "/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    //sva vozila odredjene kategorije
    //http://localhost:8080/vehicle/category?id=2
    @GetMapping(path = "/category")
    public List<Vehicle> getCategoryVehicles(@RequestParam(value="id") Long categoryId) {
        return vehicleService.getAllCategoryVehicles(categoryId);
    }

    //vozilo po id - pregled pojedinacnog vozila
    //http://localhost:8080/vehicle?id=1
    @GetMapping
    public Vehicle getVehicleById(@RequestParam(value="id") Long id) {
        return vehicleService.getVehicleById(id);
    }

    // delete http://localhost:8080/vehicle?id=1
    @DeleteMapping
    public String deleteVehicleById(@RequestParam(value="id") Long id) {
        return vehicleService.deleteVehicleById(id);
    }

    // put http://localhost:8080/vehicle?id=1
    @PutMapping(path = "/update")
    public Vehicle editVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.editVehicle(vehicle);
    }
    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }



}
