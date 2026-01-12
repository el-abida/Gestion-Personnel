package ma.ensate.rh.ensate_rh_backend.config;

import ma.ensate.rh.ensate_rh_backend.model.ResponsableRH;
import ma.ensate.rh.ensate_rh_backend.repository.ResponsableRHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private ResponsableRHRepository rhRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            createRhUserIfNotExists("admin", "admin123", "EL FAHSSI", "Chaymae", "admin@ensa.ma");
            createRhUserIfNotExists("chaymae.rh", "password123", "EL FAHSSI", "Chaymae", "chaymae@ensa.ma");
            createRhUserIfNotExists("ahmed.rh", "password123", "BENANI", "Ahmed", "ahmed.rh@ensa.ma");
            createRhUserIfNotExists("fatima.rh", "password123", "ALAOUI", "Fatima", "fatima.rh@ensa.ma");
        };
    }

    private void createRhUserIfNotExists(String username, String password, String nom, String prenom, String email) {
        if (!rhRepository.existsByUsername(username)) {
            ResponsableRH user = new ResponsableRH();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            rhRepository.save(user);
            System.out.println("User created: " + username + " (Password: " + password + ")");
        }
    }
}
