package ma.ensate.rh.ensate_rh_backend.repository;

import ma.ensate.rh.ensate_rh_backend.model.ResponsableRH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsableRHRepository extends JpaRepository<ResponsableRH, Long> {
    Optional<ResponsableRH> findByUsername(String username);
    Optional<ResponsableRH> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

