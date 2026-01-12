package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.DashboardStatsDTO;
import ma.ensate.rh.ensate_rh_backend.model.Mission;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private AvancementRepository avancementRepository;

    public DashboardStatsDTO getStats() {
        List<Personnel> allPersonnel = personnelRepository.findAll();

        int total = allPersonnel.size();
        int active = (int) allPersonnel.stream().filter(p -> p.getEstActif() != null && p.getEstActif()).count();
        int inactive = total - active;

        // Répartition par type (Fonction ou Catégorie)
        Map<String, Integer> repartition = new HashMap<>();
        for (Personnel p : allPersonnel) {
            String type = p.getTypeEmploye() != null ? p.getTypeEmploye() : "Autre";
            repartition.put(type, repartition.getOrDefault(type, 0) + 1);
        }

        // Absences en cours (non validées)
        long absencesEnCours = absenceRepository.findAll().stream()
                .filter(a -> a.getEstValideeParAdmin() != null && !a.getEstValideeParAdmin())
                .count();

        // Missions en cours (Planifiée ou En_Cours)
        long missionsEnCours = missionRepository.findAll().stream()
                .filter(m -> m.getStatut() == Mission.StatutMission.Planifiée
                        || m.getStatut() == Mission.StatutMission.En_Cours)
                .count();

        // Avancements cette année
        int currentYear = LocalDate.now().getYear();
        long avancementsAnnee = avancementRepository.findAll().stream()
                .filter(a -> a.getDateEffet() != null && a.getDateEffet().getYear() == currentYear)
                .count();

        // Moyenne ancienneté
        double moyAnciennete = allPersonnel.stream()
                .filter(p -> p.getDateRecrutementEnsa() != null)
                .mapToLong(p -> ChronoUnit.YEARS.between(p.getDateRecrutementEnsa(), LocalDate.now()))
                .average()
                .orElse(0);

        // Total congés cumulés
        int totalConges = allPersonnel.stream()
                .mapToInt(p -> p.getSoldeConges() != null ? p.getSoldeConges() : 0)
                .sum();

        return DashboardStatsDTO.builder()
                .totalPersonnel(total)
                .personnelActif(active)
                .personnelInactif(inactive)
                .absencesEnCours((int) absencesEnCours)
                .missionsEnCours((int) missionsEnCours)
                .avancementsAnnee((int) avancementsAnnee)
                .repartitionParType(repartition)
                .evolutionRecrutement(new ArrayList<>()) // À implémenter si besoin
                .moyenneAnciennete((int) moyAnciennete)
                .totalConges(totalConges)
                .build();
    }
}
