package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "responsable_rh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsableRH {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;
    
    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;
    
    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;
    
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "responsableRH", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Personnel> personnels;
}

