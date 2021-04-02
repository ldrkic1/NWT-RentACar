package ba.unsa.etf.vehiclemicroservice.demo.Services;

import ba.unsa.etf.vehiclemicroservice.demo.Model.Registered;
import ba.unsa.etf.vehiclemicroservice.demo.Repository.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisteredService {
    @Autowired
    private RegisteredRepository registeredRepository;
}