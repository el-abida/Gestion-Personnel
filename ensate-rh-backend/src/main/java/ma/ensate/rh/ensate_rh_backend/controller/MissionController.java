package ma.ensate.rh.ensate_rh_backend.controller;

import jakarta.validation.Valid;
import ma.ensate.rh.ensate_rh_backend.dto.MissionDTO;
import ma.ensate.rh.ensate_rh_backend.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@CrossOrigin(origins = "*")
public class MissionController {
    
    @Autowired
    private MissionService missionService;
    
    @PostMapping
    public ResponseEntity<MissionDTO> createMission(@Valid @RequestBody MissionDTO dto) {
        try {
            MissionDTO created = missionService.createMission(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/personnel/{id}")
    public ResponseEntity<List<MissionDTO>> getMissionsByPersonnelId(@PathVariable Long id) {
        List<MissionDTO> missions = missionService.getMissionsByPersonnelId(id);
        return ResponseEntity.ok(missions);
    }
    
    @PutMapping("/{id}/close")
    public ResponseEntity<MissionDTO> closeMission(@PathVariable Long id) {
        try {
            MissionDTO closed = missionService.closeMission(id);
            return ResponseEntity.ok(closed);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

