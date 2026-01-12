package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.ProfileDTO;
import ma.ensate.rh.ensate_rh_backend.dto.ProfileUpdateRequest;
import ma.ensate.rh.ensate_rh_backend.model.ResponsableRH;
import ma.ensate.rh.ensate_rh_backend.repository.ResponsableRHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    @Autowired
    private ResponsableRHRepository rhRepository;

    public ProfileDTO getProfile(Long id) {
        ResponsableRH rh = rhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable RH non trouvé"));

        return ProfileDTO.builder()
                .id(rh.getId())
                .nom(rh.getNom())
                .prenom(rh.getPrenom())
                .email(rh.getEmail())
                .username(rh.getUsername())
                .build();
    }

    @Transactional
    public ProfileDTO updateProfile(Long id, ProfileUpdateRequest request) {
        ResponsableRH rh = rhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable RH non trouvé"));

        // On ne modifie que l'email et le username comme demandé
        if (request.getEmail() != null) {
            rh.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            rh.setUsername(request.getUsername());
        }

        ResponsableRH updated = rhRepository.save(rh);

        return ProfileDTO.builder()
                .id(updated.getId())
                .nom(updated.getNom())
                .prenom(updated.getPrenom())
                .email(updated.getEmail())
                .username(updated.getUsername())
                .build();
    }
}
