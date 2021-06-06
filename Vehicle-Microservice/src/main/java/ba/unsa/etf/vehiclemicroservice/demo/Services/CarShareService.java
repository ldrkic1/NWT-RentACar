package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.CarShareRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarShareService {
    @Autowired
    private CarShareRepository carShareRepository;
    @Autowired
    private RegisteredRepository registeredRepository;
    public List<CarShare> getAllCarShare() {
        return carShareRepository.findAll();
    }
    public void deleteById(Long categoryId) {
        Optional<CarShare> category = carShareRepository.findById(categoryId);
        if(category.isPresent()) {
            carShareRepository.deleteById(categoryId);
        }
        else throw new NotFoundException("CarShare with id: " + categoryId + " doesn't exist.");
    }
    @Autowired
    public void MyCarShareServiceTest(CarShareRepository repository) {
        this.carShareRepository = repository;
    }
    public CarShare createNewCarShare(CarShare carShare) {
            return carShareRepository.save(carShare);
    }
    public CarShare getCarShareById(Long carShareId) {
        Optional<CarShare> category = carShareRepository.findById(carShareId);
        if(category.isPresent()) {
            return category.get();
        }
        else throw new NotFoundException("Car sharing with id: " + carShareId + " doesn't exist.");
    }
}
