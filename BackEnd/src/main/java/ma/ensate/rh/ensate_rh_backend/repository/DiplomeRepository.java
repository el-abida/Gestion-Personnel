package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    List<Diplome> findByPersonnelId(Long personnelId);
}

