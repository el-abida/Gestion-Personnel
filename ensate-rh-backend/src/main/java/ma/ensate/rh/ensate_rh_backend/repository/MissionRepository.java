package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByPersonnelId(Long personnelId);
    List<Mission> findByPersonnelIdAndStatut(Long personnelId, Mission.StatutMission statut);
}

