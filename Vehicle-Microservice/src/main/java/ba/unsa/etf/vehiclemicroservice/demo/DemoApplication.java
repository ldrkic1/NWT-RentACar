package ba.unsa.etf.vehiclemicroservice.demo;
import ba.unsa.etf.vehiclemicroservice.demo.Model.*;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
@EnableEurekaClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CarShareRepository carShareRepository, CategoryRepository categoryRepository, RegisteredRepository registeredRepository,
										ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {

		return (args) -> {
			Registered registered1 = new Registered();
			registered1.setFirstName("Goran");
			registered1.setLastName("Gotovac");
			Registered registered2 = new Registered();
			registered2.setFirstName("Niko");
			registered2.setLastName("Nikic");
			Registered registered3 = new Registered();
			registered3.setFirstName("Mujo");
			registered3.setLastName("Mujic");
			registeredRepository.saveAll(List.of(registered1, registered2, registered3));

			Category category1 = new Category();
			category1.setDescription("Putnicka");
			categoryRepository.save(category1);
			Category category2 = new Category();
			category2.setDescription("Transportna");
			categoryRepository.save(category2);
			Category category = new Category();
			category.setDescription("Luksuzna");
			categoryRepository.save(category);

			Vehicle vehicle1 = new Vehicle();
			vehicle1.setBrojSjedista(5);
			vehicle1.setCategory(category1);
			vehicle1.setModel("BMW");
			vehicle1.setPotrosnja(9);
			Vehicle vehicle2 = new Vehicle();
			vehicle2.setBrojSjedista(5);
			vehicle2.setCategory(category1);
			vehicle2.setModel("Toyota");
			vehicle2.setPotrosnja(9);
			Vehicle vehicle3 = new Vehicle();
			vehicle3.setBrojSjedista(5);
			vehicle3.setCategory(category2);
			vehicle3.setModel("Fiat Doblo");
			vehicle3.setPotrosnja(9);
			Vehicle vehicle4 = new Vehicle();
			vehicle4.setBrojSjedista(5);
			vehicle4.setCategory(category);
			vehicle4.setModel("Audi R8");
			vehicle4.setPotrosnja(15);
			vehicleRepository.saveAll(List.of(vehicle1, vehicle2, vehicle3, vehicle4));

			Reservation reservation = new Reservation();
			reservation.setVehicle(vehicle2);
			reservation.setRegistered(registered1);
			reservation.setBrojRezervacije(1232);
			reservation.setReservationStart(LocalDate.of(2021,3,23));
			reservation.setReservationEnd(LocalDate.of(2022,3,29));
			Reservation reservation1 = new Reservation();
			reservation1.setRegistered(registered2);
			reservation1.setVehicle(vehicle1);
			reservation1.setBrojRezervacije(6232);
			reservation1.setReservationStart(LocalDate.of(2021,3,20));
			reservation1.setReservationEnd(LocalDate.of(2021,3,22));
			reservation1.setCarShare(true);
			reservation.setCarShare(false);
			reservationRepository.saveAll(List.of(reservation1, reservation));

			CarShare carShare = new CarShare();
			carShare.setReservation(reservation1);
			carShare.setNumberOfFreeSpaces(4);
			carShareRepository.saveAll(List.of(carShare));
		};
	}
}
