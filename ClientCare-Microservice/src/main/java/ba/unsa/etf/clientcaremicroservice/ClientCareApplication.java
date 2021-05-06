package ba.unsa.etf.clientcaremicroservice;

import ba.unsa.etf.clientcaremicroservice.Exception.NotFoundException;
import ba.unsa.etf.clientcaremicroservice.Model.*;
import ba.unsa.etf.clientcaremicroservice.Repository.*;
import ba.unsa.etf.grpc.SystemEventResponse;
import ba.unsa.etf.grpc.SystemEventsRequest;
import ba.unsa.etf.grpc.actionGrpc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.aspectj.weaver.ast.Not;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
public class ClientCareApplication {


	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Qualifier("eurekaClient")
	@Autowired
	private EurekaClient eurekaClient;

	public static void main(String[] args) {
		SpringApplication.run(ClientCareApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


	/*@Component
	class DemoCommandLineRunner implements CommandLineRunner {
		@Autowired
		private RestTemplate restTemplate;

		@Override
		public void run(String... args) throws Exception {
			String resourceURL = "http://user-service/users/client?username=idedic";
			ResponseEntity<String> response = restTemplate.getForEntity(resourceURL, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			System.out.println("*********");
			System.out.println(root);
			User user = new User();
			user.setId(root.path("id").asLong());
			System.out.println("ID: " + user.getId());
			System.out.println("**********");

		}
	}*/
	User getUserFromUserService(String username) throws JsonProcessingException {
		if (userRepository.findByUsername(username).isPresent()) {
			return userRepository.findByUsername(username).get();
		} else {
			try {
				System.out.println("********* TRY *******");

				String resourceURL = "http://user-service/users/byUsername?username=" + username;
				ResponseEntity<String> response = restTemplate.getForEntity(resourceURL, String.class);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response.getBody());
				System.out.println("********* FROM SERVICE *******");
				System.out.println(root);
				User user = new User();
				user.setId(root.path("id").asLong());
				user.setFirstName(root.path("firstName").asText());
				user.setLastName(root.path("lastName").asText());
				user.setUsername(root.path("username").asText());

				JsonNode rootRoles = root.path("roles");
				Set<Role> roleSet = new HashSet<>();
				rootRoles.forEach(role -> {
					System.out.println("roleeee"+role.path("id").asLong() + " " + role.path("roleName").asText());
					roleSet.add(new Role(role.path("id").asLong(), role.path("roleName").asText().equals("ROLE_CLIENT") ? RoleName.ROLE_CLIENT : RoleName.ROLE_ADMIN));

					//roleSet.add(new Role(role.path("id").asLong(), RoleName.ROLE_CLIENT ));

				});

				user.setRoles(roleSet);
				System.out.println("ID: " + user.getId());
				System.out.println("**********");

				//System.out.println("ISPISIIIIIIIIII: "+);
				userRepository.save(user);
				return userRepository.findByUsername(username).get();

			} catch (HttpClientErrorException exception) {
				//throw new ValidationException(exception.getLocalizedMessage());
				System.out.println("***** Exception");
				JSONObject json = new JSONObject(exception.getResponseBodyAsString());
				throw new NotFoundException(json.get("message").toString());
			}
			catch (IllegalStateException exception) {
				System.out.println("---------------------------------------------------");
				System.out.println("Izuzetak");
				System.out.println(exception.getMessage());
				throw exception;
			}
			catch (ResourceAccessException exception) {
				System.out.println("---------------------------------------------------");
				System.out.println("Izuzetak");
				System.out.println(exception.getMessage());
				throw exception;
			}
		}
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, ReviewRepository reviewRepository, QuestionRepository questionRepository,
										AnswerRepository answerRepository, RoleRepository roleRepository) {
		return (args) -> {
			//roles
			/*Role adminRole = new Role();
			adminRole.setRoleName(RoleName.ROLE_ADMIN);
			Role clientRole = new Role();
			clientRole.setRoleName(RoleName.ROLE_CLIENT);
			Set<Role> cilentRoles = new HashSet<Role>();
			cilentRoles.add(clientRole);
			Set<Role> adminRoles = new HashSet<Role>();
			adminRoles.add(adminRole);
			Set<Role> adminUserRoles = new HashSet<Role>();
			adminUserRoles.add(adminRole);
			adminUserRoles.add(clientRole);

			//users
			User user1 = new User("Lamija", "Drkić", "ldrkic1");
			//user1.setId(100L);
			user1.setRoles(cilentRoles);
			User user2 = new User("Mujo", "Mujic", "mmujic2");
			//user2.setId(101L);
			user2.setRoles(cilentRoles);

			User userAdmin = new User("Niko", "Nikic", "nnikic123");
			userAdmin.setRoles(adminUserRoles);
			//userAdmin.setId(103L);
			User a1 = new User("Admin", "Admin", "aadmin21");
			//a1.setId(102L);
			a1.setRoles(adminRoles);
			userRepository.saveAll(List.of(a1, user1, user2, userAdmin));*/
			/*User user1=getUserFromUserService("ldrkic1");
			User user2=getUserFromUserService("mmujic2");
			User user3=getUserFromUserService("nnikic123");
			User user4=getUserFromUserService("aadmin21");
			userRepository.saveAll(List.of(user1, user2, user3, user4));*/
			//reviews
			try {
				Review review1 = new Review("Odlična vozila", "Sve pohvale, koristila sam usluge rent a car kuće više puta. Sva vozila su nova, a osoblje je jako ljubazno.", getUserFromUserService("ldrkic1"));
				Review review2 = new Review("Dugoročni najam", "Iskustvo naše kompanije sa RentACar agencijom je veoma pozitivno i to posebno u segmentu dugoročnog najma automobila. Sve pohvale!!", getUserFromUserService("mmujic2"));
				reviewRepository.save(review1);
				reviewRepository.save(review2);

				//questions
				Question question1 = new Question();
				question1.setTitle("Osiguranje automobila");
				question1.setQuestion("Da li su svi automobili osigurani?");
				question1.setUser(getUserFromUserService("mmujic2"));
				question1.setAnswered(true);
				Question question2 = new Question();
				question2.setTitle("Duzina najma");
				question2.setQuestion("Koja je minimalna duzina najma?");
				question2.setUser(getUserFromUserService("ldrkic1"));
				question2.setAnswered(true);
				Question question3 = new Question();
				question3.setTitle("Cijena najma");
				question3.setQuestion("Koja je minimalna cijena najma?");
				question3.setUser(getUserFromUserService("ldrkic1"));
				Question question4 = new Question();
				question4.setTitle("Duzina najma");
				question4.setQuestion("Koja je maksimalna duzina najma?");
				question4.setUser(getUserFromUserService("mmujic2"));
				question4.setAnswered(false);
				questionRepository.saveAll(List.of(question1, question2, question3, question4));

				//answers
				Answer answer1 = new Answer();
				answer1.setQuestion(question1);
				answer1.setUser(getUserFromUserService("aadmin21"));
				answer1.setAnswer("Prema zakonu, svi automobili, svih agencija su kasko osigurani.");

				Answer answer2 = new Answer();
				answer2.setQuestion(question2);
				answer2.setUser(getUserFromUserService("aadmin21"));
				answer2.setAnswer("Minimalna duzina najma vozila je 24h.");

				answerRepository.saveAll(List.of(answer1, answer2));
			}
			catch (Exception exception) {
				System.out.println("---------------------------------------------------");
				System.out.println("Izuzetak");
				System.out.println(exception.getMessage());

				InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("system-events", false);
				ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), 8091).usePlaintext().build();
				actionGrpc.actionBlockingStub stub=actionGrpc.newBlockingStub(channel);
				Calendar c=Calendar.getInstance();
				String ts=c.getTime().toString();
				SystemEventResponse response=stub.logAction(SystemEventsRequest.newBuilder()
						.setTimeStamp(ts)
						.setMicroservice("clientcare-service")
						.setIdKorisnik(1)
						.setAction("COMMUNICATION")
						.setResource(exception.getMessage())
						.setResponse("500")
						.build());
				System.out.println(response.getResponseTypeValue());
				channel.shutdown();
			}
		};

	}
}

