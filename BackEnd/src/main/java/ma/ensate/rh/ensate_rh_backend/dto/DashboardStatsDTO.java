package ma.ensate.rh.ensate_rh_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private int totalPersonnel;
    private int personnelActif;
    private int personnelInactif;
    private int absencesEnCours;
    private int missionsEnCours;
    private int avancementsAnnee;
    private Map<String, Integer> repartitionParType;
    private List<MonthlyDataDTO> evolutionRecrutement;
    private int moyenneAnciennete;
    private int totalConges;
}
