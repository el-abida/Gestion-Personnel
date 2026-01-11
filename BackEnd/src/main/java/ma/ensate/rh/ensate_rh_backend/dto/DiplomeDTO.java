package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensate.rh.ensate_rh_backend.model.Diplome;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiplomeDTO {
    
    private Long id;
    
    @NotBlank(message = "Diploma title is required")
    private String intitule;
    
    private String specialite;
    
    private Diplome.Niveau niveau;
    
    private String etablissement;
    
    private LocalDate dateObtention;
    
    private String fichierPreuve;
    
    @NotNull(message = "Personnel ID is required")
    private Long personnelId;
}

