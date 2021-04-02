package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.ApiRequestException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    CarShareService carShareService;
    @Autowired
    RegisteredService registeredService;
    @Autowired
    CategoryService categoryService;
    @Test
    public void getAllReservations() {
        assertTrue(reservationService.getAllReservation().size() == 2);
    }
    @Test
    public void getReservationById() {
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> reservationService.getReservationById(10L)),
                () -> assertDoesNotThrow(() -> reservationService.getReservationById(1L)),
                () -> assertEquals(2, reservationService.getAllReservation().size())
        );
    }
    @Test
    public void createNewReservation() {
        int a = reservationService.getAllReservation().size();
        Reservation reservation = new Reservation();
        Vehicle vehicle = new Vehicle();
        Registered registered = new Registered();
        Category category = categoryService.getCategoryById(1L);
        vehicle.setBrojSjedista(3);
        vehicle.setPotrosnja(7);
        vehicle.setModel("BMW");
        vehicle.setCategory(category);
        registered.setFirstName("Niko");
        registered.setLastName("Nikic");
        reservation.setCarShare(false);
        reservation.setRegistered(registered);
        reservation.setVehicle(vehicle);
        reservation.setBrojRezervacije(445);
        reservation.setReservationStart(LocalDate.parse("2021-03-20"));
        reservation.setReservationEnd(LocalDate.parse("2021-03-21"));
        reservationService.createNewReservation(reservation);
        assertEquals(3,reservationService.getAllReservation().size());
    }
    @Test
    public void createNewReservation1() {
        int a = reservationService.getAllReservation().size();
        Reservation reservation = new Reservation();
        Vehicle vehicle = new Vehicle();
        Registered registered = new Registered();
        Category category = categoryService.getCategoryById(1L);
        vehicle.setBrojSjedista(3);
        vehicle.setPotrosnja(7);
        vehicle.setModel("BMW");
        vehicle.setCategory(category);
        registered.setFirstName("Niko");
        registered.setLastName("Nikic");
        reservation.setCarShare(false);
        reservation.setRegistered(registered);
        reservation.setVehicle(vehicle);
        reservation.setBrojRezervacije(445);
        reservation.setReservationStart(LocalDate.parse("2021-03-20"));
        reservation.setReservationEnd(LocalDate.parse("2021-03-20"));
        assertAll(
                () -> assertThrows(
                        ValidationException.class,
                        () -> reservationService.createNewReservation(reservation)));
    }
    @Test
    public void createNewReservation2() {
        int a = reservationService.getAllReservation().size();
        Reservation reservation = new Reservation();
        Vehicle vehicle = new Vehicle();
        Registered registered = new Registered();
        Category category = categoryService.getCategoryById(1L);
        vehicle.setBrojSjedista(3);
        vehicle.setPotrosnja(7);
        vehicle.setModel("BMW");
        vehicle.setCategory(category);
        registered.setFirstName("Ivan");
        registered.setLastName("Ivanic");
        reservation.setCarShare(false);
        reservation.setRegistered(registered);
        reservation.setVehicle(vehicle);
        reservation.setBrojRezervacije(445);
        reservation.setReservationStart(LocalDate.parse("2021-03-20"));
        reservation.setReservationEnd(LocalDate.parse("2021-03-21"));
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> reservationService.createNewReservation(reservation)));
    }
    public void createNewReservation3() {
        int a = reservationService.getAllReservation().size();
        Reservation reservation = new Reservation();
        Registered registered = new Registered();
        registered.setFirstName("Niko");
        registered.setLastName("Nikic");
        reservation.setCarShare(false);
        reservation.setRegistered(registered);
        reservation.setBrojRezervacije(445);
        reservation.setReservationStart(LocalDate.parse("2021-03-20"));
        reservation.setReservationEnd(LocalDate.parse("2021-03-21"));
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> reservationService.createNewReservation(reservation)));
    }
}
