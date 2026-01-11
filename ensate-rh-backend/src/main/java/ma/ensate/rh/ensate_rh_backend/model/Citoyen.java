package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "citoyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Citoyen {
    
    @Id
    @Column(name = "cin", length = 20)
    private String cin;
    
    @Column(name = "nom_fr", length = 100)
    private String nomFr;
    
    @Column(name = "nom_ar", length = 100)
    private String nomAr;
    
    @Column(name = "prenom_fr", length = 100)
    private String prenomFr;
    
    @Column(name = "prenom_ar", length = 100)
    private String prenomAr;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    
    @Column(name = "lieu_naissance", length = 100)
    private String lieuNaissance;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", length = 20)
    private Sexe sexe;
    
    @Column(name = "photo_url", length = 255)
    private String photoUrl;
    
    public enum Sexe {
        Masculin, Féminin
    }
}

