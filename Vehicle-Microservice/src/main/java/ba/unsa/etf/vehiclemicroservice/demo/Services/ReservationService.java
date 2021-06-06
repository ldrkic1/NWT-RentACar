package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.ValidationException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Registered;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Reservation;
import ba.unsa.etf.vehiclemicroservice.demo.Model.Vehicle;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.CarShareRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.RegisteredRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.ReservationRepository;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RegisteredRepository registeredRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CarShareRepository carShareRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Reservation createNewReservation(Reservation reservation) throws JSONException {
        if(reservation.getRegistered()== null)
            throw new NotFoundException("No registered user");
        if(reservation.getVehicle() == null)
            throw new NotFoundException("No vehicle");
        if(reservation.getRegistered().getUsername()=="") {
            throw new NotFoundException("Please enter a username");
        }
        Optional<Registered> registered = registeredRepository.getUserByUsername(reservation.getRegistered().getUsername());
        Optional<Vehicle> vehicle = vehicleRepository.findByModel(reservation.getVehicle().getModel());
        if(registered.isPresent()) {
            if (vehicle.isPresent()) {
                if (!reservation.getReservationStart().isEqual(reservation.getReservationEnd())) {
                    reservation.setVehicle(vehicle.get());
                    reservation.setRegistered(registered.get());

                    Reservation r = reservationRepository.save(reservation);
                    if (r.isCarShare()) {
                        CarShare carShare = new CarShare();
                        carShare.setNumberOfFreeSpaces(reservation.getVehicle().getBrojSjedista() - 1);
                        carShare.setReservation(reservation);
                        carShareRepository.save(carShare);
                    }
                    return r;
                } else throw new ValidationException("Ending date can't be the same as the starting date");
            } else throw new NotFoundException("No vehicle");
        }
        else {

            String url = "http://user-service/users/client?username=" + reservation.getRegistered().getUsername();
            try{
                ResponseEntity<String> userResponse = restTemplate.getForEntity(url, String.class);
                //status je OK - user postoji
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(userResponse.getBody());
                Registered newUser = new Registered();
                System.out.println(root.path("lastName").asText());
                newUser.setId(root.path("id").asLong());
                newUser.setLastName(root.path("lastName").asText());
                newUser.setUsername(root.path("username").asText());
                newUser.setFirstName(root.path("firstName").asText());
                newUser.setLastName(root.path("lastName").asText());
                newUser.setUsername(root.path("username").asText());
                if(reservation.getReservationStart().isEqual(reservation.getReservationEnd())) {
                    throw new ValidationException("Ending date can't be the same as the starting date");
                }
                if(newUser.getUsername()=="") {
                    throw new NotFoundException("Please enter a username");
                }
                if (!vehicle.isPresent()) {
                    throw new NotFoundException("No vehicle");
                }
                System.out.println("DOSAO");
                registeredRepository.save(newUser);
                System.out.println("DOSAO2");
                reservation.setVehicle(vehicle.get());
                reservation.setRegistered(newUser);
                System.out.println("NAPRAVIO REGISTROVANOG");
                Reservation r = reservationRepository.save(reservation);
                System.out.println("NAPRAVIO REZERVACIJU");
                if (r.isCarShare()) {
                    CarShare carShare = new CarShare();
                    carShare.setNumberOfFreeSpaces(reservation.getVehicle().getBrojSjedista() - 1);
                    carShare.setReservation(reservation);
                    carShareRepository.save(carShare);
                }
                return r;
            } catch (HttpClientErrorException e) {
                //user ne postoji u bazi user servisa
                JSONObject json = new JSONObject(e.getResponseBodyAsString());
                throw new NotFoundException(json.get("message").toString());
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public Long numberOfReservationsWithTheSameStart(LocalDate date) {
        return reservationRepository.findByReservationStart(date);

    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isPresent()) {
            return reservation.get();
        }
        else throw new NotFoundException("Reservation with id: " + reservationId + " doesn't exist.");

    }
    public Registered getReservation(String id) throws JsonProcessingException {
        ResponseEntity<String> resp =
                restTemplate.getForEntity("http://user-service/users/client?username=" + id, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resp.getBody());
        Registered newUser = new Registered();
        newUser.setId(root.path("id").asLong());
        newUser.setLastName(root.path("lastName").asText());
        newUser.setUsername(root.path("username").asText());
        newUser.setFirstName(root.path("firstName").asText());
        newUser.setLastName(root.path("lastName").asText());
        newUser.setUsername(root.path("username").asText());
        return  newUser;
    }

    public List<Reservation> getReservationByRegisteredId(Long reservationId) {
        List<Reservation> reservacije =  reservationRepository.getReservationsByRegisteredId(reservationId);
        if(reservacije.isEmpty())
            System.out.println("BANANA");
        return reservacije;
    }
    public Optional<Registered> getRegistered(String username) {
        return registeredRepository.getUserByUsername(username);
    }

    public String deleteVehicleById(Long id) {
        Optional<Reservation> optional = reservationRepository.findById(id);
        if(optional.isPresent()) {
            if(optional.get().isCarShare()) {
                carShareRepository.deleteCareShareReservations(id);
                reservationRepository.deleteById(id);
                return "Reservation deleted successfully!";
            }
            else {
                reservationRepository.deleteById(id);
                return "Reservation deleted successfully!";
            }
        }
        else return "Reservation can't be deleted!";
        }

}