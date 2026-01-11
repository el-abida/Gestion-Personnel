package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByPersonnelId(Long personnelId);
    List<Absence> findByPersonnelIdAndEstValideeParAdmin(Long personnelId, Boolean estValideeParAdmin);
}

