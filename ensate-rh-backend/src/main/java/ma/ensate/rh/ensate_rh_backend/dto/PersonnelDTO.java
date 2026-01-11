package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelDTO {
    
    private Long id;
    
    @Valid
    @NotNull(message = "Citizen information is required")
    private CitoyenDTO citoyen;
    
    @NotBlank(message = "PPR is required")
    private String ppr;
    
    private String typeEmploye;
    
    private LocalDate dateRecrutementMinistere;
    
    private LocalDate dateRecrutementEnsa;
    
    private String gradeActuel;
    
    private Integer echelleActuelle;
    
    private Integer echelonActuel;
    
    private Integer soldeConges = 0;
    
    private Boolean estActif = true;
}

