package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "mission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "destination", length = 150, nullable = false)
    private String destination;
    
    @Column(name = "objet_mission", length = 255, nullable = false)
    private String objetMission;
    
    @Column(name = "date_depart", nullable = false)
    private LocalDate dateDepart;
    
    @Column(name = "date_retour")
    private LocalDate dateRetour;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20)
    private StatutMission statut = StatutMission.Planifiée;
    
    @Column(name = "rapport_url", length = 255)
    private String rapportUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    private Personnel personnel;
    
    public enum StatutMission {
        Planifiée, En_Cours, Terminée, Annulée
    }
}

