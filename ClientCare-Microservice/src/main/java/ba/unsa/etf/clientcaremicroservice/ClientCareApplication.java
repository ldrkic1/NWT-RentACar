package ba.unsa.etf.clientcaremicroservice;

import ba.unsa.etf.clientcaremicroservice.Model.*;
import ba.unsa.etf.clientcaremicroservice.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ClientCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientCareApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, ReviewRepository reviewRepository, QuestionRepository questionRepository,
										AnswerRepository answerRepository, RoleRepository roleRepository) {
		return (args) -> {
			//roles
			Role adminRole = new Role();
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
			User user1 = new User("Lamija", "Drkić");
			user1.setRoles(cilentRoles);
			User user2 = new User("Mujo", "Mujic");
			user2.setRoles(cilentRoles);

			User userAdmin = new User("Niko", "Nikic");
			userAdmin.setRoles(adminUserRoles);
			User a1 = new User("Admin","Admin");
			a1.setRoles(adminRoles);
			userRepository.saveAll(List.of(a1, user1, user2, userAdmin));

			//reviews
			Review review1 = new Review("Odlična vozila", "Sve pohvale, koristila sam usluge rent a car kuće više puta. Sva vozila su nova, a osoblje je jako ljubazno.", user1);
			Review review2 = new Review("Dugoročni najam", "Iskustvo naše kompanije sa RentACar agencijom je veoma pozitivno i to posebno u segmentu dugoročnog najma automobila. Sve pohvale!!", user2);
			reviewRepository.save(review1);
			reviewRepository.save(review2);

			//questions
			Question question1 = new Question();
			question1.setTitle("Osiguranje automobila");
			question1.setQuestion("Da li su svi automobili osigurani?");
			question1.setUser(user2);
			question1.setAnswered(true);
			Question question2 = new Question();
			question2.setTitle("Duzina najma");
			question2.setQuestion("Koja je minimalna duzina najma?");
			question2.setUser(user1);
			question2.setAnswered(true);
			Question question3 = new Question();
			question3.setTitle("Cijena najma");
			question3.setQuestion("Koja je minimalna cijena najma?");
			question3.setUser(user1);
			Question question4 = new Question();
			question4.setTitle("Duzina najma");
			question4.setQuestion("Koja je maksimalna duzina najma?");
			question4.setUser(user2);
			question4.setAnswered(false);
			questionRepository.saveAll(List.of(question1,question2,question3,question4));

			//answers
			Answer answer1 = new Answer();
			answer1.setQuestion(question1);
			answer1.setUser(a1);
			answer1.setAnswer("Prema zakonu, svi automobili, svih agencija su kasko osigurani.");

			Answer answer2 = new Answer();
			answer2.setQuestion(question2);
			answer2.setUser(a1);
			answer2.setAnswer("Minimalna duzina najma vozila je 24h.");

			answerRepository.saveAll(List.of(answer1,answer2));
		};

	}
}
