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
    
    @GetMapping("/personnel/{id}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByPersonnelId(@PathVariable Long id) {
        List<AbsenceDTO> absences = absenceService.getAbsencesByPersonnelId(id);
        return ResponseEntity.ok(absences);
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

