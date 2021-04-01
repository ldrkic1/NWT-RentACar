package ba.unsa.etf.notificationmicroservice.controllers;

import ba.unsa.etf.notificationmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
}
