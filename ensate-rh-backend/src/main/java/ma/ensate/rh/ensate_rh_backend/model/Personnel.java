package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personnel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personnel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cin", referencedColumnName = "cin", nullable = false, unique = true)
    private Citoyen citoyen;
    
    @Column(name = "ppr", length = 30, unique = true)
    private String ppr;
    
    @Column(name = "type_employe", length = 50)
    private String typeEmploye;
    
    @Column(name = "date_recrutement_ministere")
    private LocalDate dateRecrutementMinistere;
    
    @Column(name = "date_recrutement_ensa")
    private LocalDate dateRecrutementEnsa;
    
    @Column(name = "grade_actuel", length = 50)
    private String gradeActuel;
    
    @Column(name = "echelle_actuelle")
    private Integer echelleActuelle;
    
    @Column(name = "echelon_actuel")
    private Integer echelonActuel;
    
    @Column(name = "solde_conges")
    private Integer soldeConges = 0;
    
    @Column(name = "est_actif")
    private Boolean estActif = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_rh_id")
    private ResponsableRH responsableRH;
    
    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diplome> diplomes;
    
    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avancement> avancements;
    
    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Absence> absences;
    
    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missions;
}

