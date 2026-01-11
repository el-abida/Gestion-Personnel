package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "avancement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avancement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_decision", nullable = false)
    private LocalDate dateDecision;
    
    @Column(name = "date_effet", nullable = false)
    private LocalDate dateEffet;
    
    @Column(name = "grade_precedent", length = 50)
    private String gradePrecedent;
    
    @Column(name = "grade_nouveau", length = 50, nullable = false)
    private String gradeNouveau;
    
    @Column(name = "echelle_precedente")
    private Integer echellePrecedente;
    
    @Column(name = "echelle_nouvelle")
    private Integer echelleNouvelle;
    
    @Column(name = "echelon_precedent")
    private Integer echelonPrecedent;
    
    @Column(name = "echelon_nouveau")
    private Integer echelonNouveau;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    private Personnel personnel;
}

