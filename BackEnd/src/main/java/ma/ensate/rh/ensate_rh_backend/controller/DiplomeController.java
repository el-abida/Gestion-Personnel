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

