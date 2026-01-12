package ma.ensate.rh.ensate_rh_backend.controller;

import jakarta.validation.Valid;
import ma.ensate.rh.ensate_rh_backend.dto.DiplomeDTO;
import ma.ensate.rh.ensate_rh_backend.service.DiplomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diplomes")
@CrossOrigin(origins = "*")
public class DiplomeController {

    @Autowired
    private DiplomeService diplomeService;

    @GetMapping("/personnel/{id}")
    public ResponseEntity<List<DiplomeDTO>> getDiplomesByPersonnelId(@PathVariable Long id) {
        List<DiplomeDTO> diplomes = diplomeService.getDiplomesByPersonnelId(id);
        return ResponseEntity.ok(diplomes);
    }

    @GetMapping
    public ResponseEntity<List<DiplomeDTO>> getAllDiplomes() {
        List<DiplomeDTO> diplomes = diplomeService.getAllDiplomes();
        return ResponseEntity.ok(diplomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiplomeDTO> getDiplomeById(@PathVariable Long id) {
        try {
            DiplomeDTO diplome = diplomeService.getDiplomeById(id);
            return ResponseEntity.ok(diplome);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiplomeDTO> updateDiplome(@PathVariable Long id, @Valid @RequestBody DiplomeDTO dto) {
        try {
            DiplomeDTO updated = diplomeService.updateDiplome(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<DiplomeDTO> createDiplome(@Valid @RequestBody DiplomeDTO dto) {
        try {
            DiplomeDTO created = diplomeService.createDiplome(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiplome(@PathVariable Long id) {
        try {
            diplomeService.deleteDiplome(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
