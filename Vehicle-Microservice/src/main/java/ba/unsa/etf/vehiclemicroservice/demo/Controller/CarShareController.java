package ba.unsa.etf.vehiclemicroservice.demo.Controller;


import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;
import ba.unsa.etf.vehiclemicroservice.demo.Services.CarShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carshare")
public class CarShareController {
    private final CarShareService carShare;
    @Autowired
    public CarShareController(CarShareService carShareService) { this.carShare = carShareService; }
    @GetMapping(path="/all")
    public List<CarShare> getAllCarShare() {
        return carShare.getAllCarShare();
    }
    @DeleteMapping("/{id}")
    public String deleteCarShareById(@PathVariable Long id){
        CarShare carShare1 = carShare.getCarShareById(id);
        carShare.deleteById(id);
        return "deleted Category by ID";
    }
    @PostMapping
    public CarShare createNewCarShare(@RequestBody CarShare carShare1) {
        return carShare.createNewCarShare(carShare1);
    }
    @GetMapping
    public CarShare getCarShareById(@RequestParam(value = "id") Long carShareId) {
        return carShare.getCarShareById(carShareId);
    }
}