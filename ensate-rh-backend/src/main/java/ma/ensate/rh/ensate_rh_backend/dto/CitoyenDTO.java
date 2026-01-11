package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensate.rh.ensate_rh_backend.model.Citoyen;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitoyenDTO {
    
    @NotBlank(message = "CIN is required")
    private String cin;
    
    private String nomFr;
    
    private String nomAr;
    
    private String prenomFr;
    
    private String prenomAr;
    
    private String adresse;
    
    private String email;
    
    private String telephone;
    
    private LocalDate dateNaissance;
    
    private String lieuNaissance;
    
    private Citoyen.Sexe sexe;
    
    private String photoUrl;
}

