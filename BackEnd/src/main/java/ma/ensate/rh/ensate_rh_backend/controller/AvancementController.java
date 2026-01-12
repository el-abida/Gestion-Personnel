package ma.ensate.rh.ensate_rh_backend.controller;

import jakarta.validation.Valid;
import ma.ensate.rh.ensate_rh_backend.dto.AvancementDTO;
import ma.ensate.rh.ensate_rh_backend.service.AvancementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avancements")
@CrossOrigin(origins = "*")
public class AvancementController {

    @Autowired
    private AvancementService avancementService;

    @GetMapping
    public ResponseEntity<List<AvancementDTO>> getAllAvancements() {
        return ResponseEntity.ok(avancementService.getAllAvancements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvancementDTO> getAvancementById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(avancementService.getAvancementById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personnel/{id}")
    public ResponseEntity<List<AvancementDTO>> getAvancementsByPersonnelId(@PathVariable Long id) {
        List<AvancementDTO> avancements = avancementService.getAvancementsByPersonnelId(id);
        return ResponseEntity.ok(avancements);
    }

    @PostMapping
    public ResponseEntity<AvancementDTO> createAvancement(@Valid @RequestBody AvancementDTO dto) {
        try {
            AvancementDTO created = avancementService.createAvancement(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvancementDTO> updateAvancement(@PathVariable Long id,
            @Valid @RequestBody AvancementDTO dto) {
        try {
            return ResponseEntity.ok(avancementService.updateAvancement(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvancement(@PathVariable Long id) {
        avancementService.deleteAvancement(id);
        return ResponseEntity.noContent().build();
    }
}
