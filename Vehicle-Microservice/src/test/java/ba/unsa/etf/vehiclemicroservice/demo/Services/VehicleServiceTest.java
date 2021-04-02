package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.ApiRequestException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Category;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ReservationService reservationService;
    @Test
    public void getAllVehicle() {
        assertTrue(vehicleService.getAllVehicles().size() == 3);
    }
    @Test
    public void getVehiclesByCategoryId() {
        assertTrue( vehicleService.getAllCategoryVehicles((long) 2).size() == 2);
    }
    @Test
    public void getVehicleId() {
        assertEquals("BMW", vehicleService.getVehicleById(1L).getModel());
    }
    @Test
    public void deleteVehicleById() {
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> vehicleService.deleteVehicleById(10L)),
                () -> assertDoesNotThrow(() -> vehicleService.deleteVehicleById(1L)),
                () -> assertEquals(4, vehicleService.getAllVehicles().size())
        );
    }
    @Test
    public void deleteVehicleById2() {
        assertAll(
                () -> assertThrows(
                        ApiRequestException.class,
                        () -> vehicleService.deleteVehicleById(2L)),
                () -> assertDoesNotThrow(() -> vehicleService.deleteVehicleById(4L)),
                () -> assertEquals(3, vehicleService.getAllVehicles().size())
        );
    }
    @Test
    public void editVehicle() {
        Vehicle vehicle = vehicleService.getVehicleById(2L);
        vehicle.setPotrosnja(8);
        vehicleService.editVehicle(2L,vehicle);
        assertEquals(8,vehicleService.getVehicleById(2L).getPotrosnja());
        Vehicle vehicle1 = vehicleService.getVehicleById(3L);
        vehicle1.setBrojSjedista(1);
        assertAll(
                ()-> assertThrows(
                        ValidationException.class,
                        () -> vehicleService.editVehicle(2L,vehicle1))
        );
    }
    @Test
    public void addVehicle() {
        int a = vehicleService.getAllVehicles().size();
        Category category = categoryService.getCategoryById(2L);
        Vehicle vehicle = new Vehicle();
        vehicle.setBrojSjedista(3);
        vehicle.setPotrosnja(7);
        vehicle.setModel("Audi");
        vehicle.setCategory(category);
        vehicleService.addVehicle(vehicle);
        assertEquals(a+1, vehicleService.getAllVehicles().size());
        Vehicle vehicle1 = new Vehicle();
        vehicle.setModel("");
        vehicle.setBrojSjedista(3);
        vehicle.setPotrosnja(7);
        vehicle.setCategory(category);
        /*assertAll(
                ()-> assertThrows(
                        ValidationException.class,
                        () -> vehicleService.addVehicle(vehicle1))
        );
        Vehicle vehicle2 = new Vehicle();
        vehicle.setBrojSjedista(1);
        vehicle.setModel("Audi");
        vehicle.setPotrosnja(7);
        vehicle.setCategory(category);
        assertAll(
                ()-> assertThrows(
                        ValidationException.class,
                        () -> vehicleService.addVehicle(vehicle2))
        );*/

    }
}
