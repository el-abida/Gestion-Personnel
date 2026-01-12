package ma.ensate.rh.ensate_rh_backend.controller;

import jakarta.validation.Valid;
import ma.ensate.rh.ensate_rh_backend.dto.PersonnelDTO;
import ma.ensate.rh.ensate_rh_backend.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnels")
@CrossOrigin(origins = "*")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public ResponseEntity<List<PersonnelDTO>> getAllPersonnel() {
        List<PersonnelDTO> personnels = personnelService.getAllPersonnel();
        return ResponseEntity.ok(personnels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelDTO> getPersonnelById(@PathVariable Long id) {
        try {
            PersonnelDTO personnel = personnelService.getPersonnelById(id);
            return ResponseEntity.ok(personnel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PersonnelDTO> createPersonnel(@Valid @RequestBody PersonnelDTO dto) {
        try {
            PersonnelDTO created = personnelService.createPersonnel(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonnelDTO> updatePersonnel(@PathVariable Long id, @Valid @RequestBody PersonnelDTO dto) {
        try {
            PersonnelDTO updated = personnelService.updatePersonnel(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        try {
            personnelService.deletePersonnel(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonnelDTO>> searchPersonnel(@RequestParam String query) {
        return ResponseEntity.ok(personnelService.searchPersonnel(query));
    }
}
