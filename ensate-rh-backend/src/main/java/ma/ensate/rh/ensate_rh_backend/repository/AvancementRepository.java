package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Avancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvancementRepository extends JpaRepository<Avancement, Long> {
    List<Avancement> findByPersonnelIdOrderByDateEffetDesc(Long personnelId);
}

