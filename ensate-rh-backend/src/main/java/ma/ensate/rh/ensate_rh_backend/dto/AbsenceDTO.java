package ma.ensate.rh.ensate_rh_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensate.rh.ensate_rh_backend.model.Absence;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceDTO {
    
    private Long id;
    
    @NotNull(message = "Start date is required")
    private LocalDate dateDebut;
    
    @NotNull(message = "End date is required")
    private LocalDate dateFin;
    
    @NotNull(message = "Absence type is required")
    private Absence.TypeAbsence type;
    
    private String motif;
    
    private String justificatifUrl;
    
    private Boolean estValideeParAdmin = false;
    
    @NotNull(message = "Personnel ID is required")
    private Long personnelId;
}

