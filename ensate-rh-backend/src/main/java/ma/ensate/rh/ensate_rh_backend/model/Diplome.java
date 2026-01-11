package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "diplome")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diplome {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "intitule", length = 100, nullable = false)
    private String intitule;
    
    @Column(name = "specialite", length = 100)
    private String specialite;
    
    @Convert(converter = NiveauConverter.class)
    @Column(name = "niveau", length = 20)
    private Niveau niveau;
    
    @Column(name = "etablissement", length = 150)
    private String etablissement;
    
    @Column(name = "date_obtention")
    private LocalDate dateObtention;
    
    @Column(name = "fichier_preuve", length = 255)
    private String fichierPreuve;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    private Personnel personnel;
    
    public enum Niveau {
        Bac, 
        Bac_Plus_2, 
        Licence, 
        Master, 
        Ingénieur, 
        Doctorat
    }
}

