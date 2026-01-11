package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvancementDTO {
    
    private Long id;
    
    @NotNull(message = "Decision date is required")
    private LocalDate dateDecision;
    
    @NotNull(message = "Effective date is required")
    private LocalDate dateEffet;
    
    private String gradePrecedent;
    
    @NotNull(message = "New grade is required")
    private String gradeNouveau;
    
    private Integer echellePrecedente;
    
    private Integer echelleNouvelle;
    
    private Integer echelonPrecedent;
    
    private Integer echelonNouveau;
    
    private String description;
    
    @NotNull(message = "Personnel ID is required")
    private Long personnelId;
}

