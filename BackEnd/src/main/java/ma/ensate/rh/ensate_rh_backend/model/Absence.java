package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "absence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Absence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private TypeAbsence type;
    
    @Column(name = "motif", columnDefinition = "TEXT")
    private String motif;
    
    @Column(name = "justificatif_url", length = 255)
    private String justificatifUrl;
    
    @Column(name = "est_validee_par_admin")
    private Boolean estValideeParAdmin = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    private Personnel personnel;
    
    public enum TypeAbsence {
        Maladie, Congé_Annuel, Exceptionnelle, Non_Justifiée
    }
}

