package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    
    @EntityGraph(attributePaths = {"citoyen"})
    @Override
    List<Personnel> findAll();
    
    @EntityGraph(attributePaths = {"citoyen"})
    @Override
    Optional<Personnel> findById(Long id);
    
    @EntityGraph(attributePaths = {"citoyen"})
    Optional<Personnel> findByPpr(String ppr);
    
    @EntityGraph(attributePaths = {"citoyen"})
    Optional<Personnel> findByCitoyen_Cin(String cin);
    
    List<Personnel> findByEstActif(Boolean estActif);
    boolean existsByPpr(String ppr);
    boolean existsByCitoyen_Cin(String cin);
}

