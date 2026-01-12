package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    @EntityGraph(attributePaths = { "citoyen" })
    @Override
    List<Personnel> findAll();

    @EntityGraph(attributePaths = { "citoyen" })
    @Override
    Optional<Personnel> findById(Long id);

    @EntityGraph(attributePaths = { "citoyen" })
    Optional<Personnel> findByPpr(String ppr);

    @EntityGraph(attributePaths = { "citoyen" })
    Optional<Personnel> findByCitoyen_Cin(String cin);

    @EntityGraph(attributePaths = { "citoyen" })
    @Query("SELECT p FROM Personnel p JOIN p.citoyen c WHERE " +
            "LOWER(p.ppr) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.nomFr) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.prenomFr) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(c.prenomFr, ' ', c.nomFr)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(c.nomFr, ' ', c.prenomFr)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.nomAr) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.prenomAr) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(c.prenomAr, ' ', c.nomAr)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(c.nomAr, ' ', c.prenomAr)) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Personnel> search(@Param("query") String query);

    List<Personnel> findByEstActif(Boolean estActif);

    boolean existsByPpr(String ppr);

    boolean existsByCitoyen_Cin(String cin);
}
