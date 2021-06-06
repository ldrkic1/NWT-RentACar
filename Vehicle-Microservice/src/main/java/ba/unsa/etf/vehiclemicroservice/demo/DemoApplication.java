package ba.unsa.etf.vehiclemicroservice.demo;

import ba.unsa.etf.vehiclemicroservice.demo.Model.*;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	CommandLineRunner commandLineRunner(CarShareRepository carShareRepository, CategoryRepository categoryRepository, RegisteredRepository registeredRepository,
										ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {

		return (args) -> {
			Registered registered1 = new Registered();
			registered1.setId(1L);
			registered1.setFirstName("Goran");
			registered1.setLastName("Gotovac");
			registered1.setUsername("Gogi");
			Registered registered2 = new Registered();
			registered2.setId(2L);
			registered2.setFirstName("Niko");
			registered2.setLastName("Nikic");
			registered2.setUsername("NikoNikic");
			Registered registered3 = new Registered();
			registered3.setId(3L);
			registered3.setFirstName("Mujo");
			registered3.setLastName("Mujic");
			registered3.setUsername("MujoMujic");
			registeredRepository.saveAll(List.of(registered1, registered2, registered3));

			Category category1 = new Category();
			category1.setDescription("Luksuzna");
			categoryRepository.save(category1);
			Category category2 = new Category();
			category2.setDescription("Sedan");
			categoryRepository.save(category2);
			Category category = new Category();
			category.setDescription("Hatchback");
			categoryRepository.save(category);
			Category category3 = new Category();
			category3.setDescription("StationWagon");
			categoryRepository.save(category3);

			Vehicle vehicle5 = new Vehicle();
			vehicle5.setBrojSjedista(5);
			vehicle5.setCategory(category3);
			vehicle5.setModel("Ford");
			vehicle5.setPotrosnja(9);
			vehicle5.setURL("https://www.motorionline.com/wp-content/uploads/2015/02/ford-focus-rs-station-wagon-rendering-1024x633.jpg");

			Vehicle vehicle6 = new Vehicle();
			vehicle6.setBrojSjedista(5);
			vehicle6.setCategory(category3);
			vehicle6.setModel("Dacia");
			vehicle6.setPotrosnja(9);
			vehicle6.setURL("https://www.motorionline.com/wp-content/uploads/2013/10/Dacia-Logan-MCV-2013-1024x633.jpg");


			Vehicle vehicle1 = new Vehicle();
			vehicle1.setBrojSjedista(5);
			vehicle1.setCategory(category1);
			vehicle1.setModel("BMW");
			vehicle1.setPotrosnja(9);
			vehicle1.setURL("https://www.automotiveaddicts.com/wp-content/uploads/2017/12/2018-bmw-640i-gt-1024x633.jpg");
			Vehicle vehicle2 = new Vehicle();
			vehicle2.setBrojSjedista(5);
			vehicle2.setCategory(category2);
			vehicle2.setModel("Audi");
			vehicle2.setPotrosnja(9);
			vehicle2.setURL("https://www.mojvolan.com/wp-content/uploads/2018/10/Audi-RS6-2020-1.jpg");
			Vehicle vehicle3 = new Vehicle();
			vehicle3.setBrojSjedista(5);
			vehicle3.setCategory(category);
			vehicle3.setModel("Skoda");
			vehicle3.setPotrosnja(9);
			vehicle3.setURL("https://www.incomod.info/wp-content/uploads/2020/06/Skoda-Scala-2-1-1024x633.jpg");
			Vehicle vehicle4 = new Vehicle();
			vehicle4.setBrojSjedista(5);
			vehicle4.setCategory(category1);
			vehicle4.setModel("Opel");
			vehicle4.setPotrosnja(3);
			vehicle4.setURL("https://www.motorionline.com/wp-content/uploads/2015/06/Opel-Astra-2016-foto-web-2-1024x633.jpg");
			vehicleRepository.saveAll(List.of(vehicle1, vehicle2, vehicle3, vehicle4,vehicle5, vehicle6));

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
