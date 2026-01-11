package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.LoginRequest;
import ma.ensate.rh.ensate_rh_backend.dto.LoginResponse;
import ma.ensate.rh.ensate_rh_backend.model.ResponsableRH;
import ma.ensate.rh.ensate_rh_backend.repository.ResponsableRHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private ResponsableRHRepository responsableRHRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest request) {
        Optional<ResponsableRH> responsableRH = responsableRHRepository.findByUsername(request.getUsername());
        
        if (responsableRH.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }
        
        ResponsableRH rh = responsableRH.get();
        
        if (!passwordEncoder.matches(request.getPassword(), rh.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        LoginResponse response = new LoginResponse();
        response.setId(rh.getId());
        response.setNom(rh.getNom());
        response.setPrenom(rh.getPrenom());
        response.setEmail(rh.getEmail());
        response.setUsername(rh.getUsername());
        response.setToken("token_" + rh.getId()); // Simple token for now, can be replaced with JWT
        
        return response;
    }
}

