package ba.unsa.etf.Models;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
