package ma.ensate.rh.ensate_rh_backend.controller;

import jakarta.validation.Valid;
import ma.ensate.rh.ensate_rh_backend.dto.AbsenceDTO;
import ma.ensate.rh.ensate_rh_backend.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @PostMapping
    public ResponseEntity<AbsenceDTO> createAbsence(@Valid @RequestBody AbsenceDTO dto) {
        try {
            AbsenceDTO created = absenceService.createAbsence(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences() {
        return ResponseEntity.ok(absenceService.getAllAbsences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> getAbsenceById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(absenceService.getAbsenceById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personnel/{id}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByPersonnelId(@PathVariable Long id) {
        List<AbsenceDTO> absences = absenceService.getAbsencesByPersonnelId(id);
        return ResponseEntity.ok(absences);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDTO> updateAbsence(@PathVariable Long id, @Valid @RequestBody AbsenceDTO dto) {
        try {
            return ResponseEntity.ok(absenceService.updateAbsence(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<AbsenceDTO> validateAbsence(@PathVariable Long id) {
        try {
            AbsenceDTO validated = absenceService.validateAbsence(id);
            return ResponseEntity.ok(validated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
