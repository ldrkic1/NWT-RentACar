package ba.unsa.etf.vehiclemicroservice.demo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

    @Value("${message:Hello from vehicle service}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}
