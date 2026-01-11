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
}

