package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensate.rh.ensate_rh_backend.model.Mission;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionDTO {
    
    private Long id;
    
    @NotBlank(message = "Destination is required")
    private String destination;
    
    @NotBlank(message = "Mission object is required")
    private String objetMission;
    
    @NotNull(message = "Departure date is required")
    private LocalDate dateDepart;
    
    private LocalDate dateRetour;
    
    private Mission.StatutMission statut = Mission.StatutMission.Planifiée;
    
    private String rapportUrl;
    
    @NotNull(message = "Personnel ID is required")
    private Long personnelId;
}

