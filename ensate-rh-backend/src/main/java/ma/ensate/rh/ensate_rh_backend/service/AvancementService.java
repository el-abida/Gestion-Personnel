package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.AvancementDTO;
import ma.ensate.rh.ensate_rh_backend.model.Avancement;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.AvancementRepository;
import ma.ensate.rh.ensate_rh_backend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvancementService {
    
    @Autowired
    private AvancementRepository avancementRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    public List<AvancementDTO> getAvancementsByPersonnelId(Long personnelId) {
        return avancementRepository.findByPersonnelIdOrderByDateEffetDesc(personnelId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public AvancementDTO createAvancement(AvancementDTO dto) {
        Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));
        
        // Save current grade as previous grade
        Avancement avancement = new Avancement();
        avancement.setDateDecision(dto.getDateDecision());
        avancement.setDateEffet(dto.getDateEffet());
        avancement.setGradePrecedent(personnel.getGradeActuel());
        avancement.setGradeNouveau(dto.getGradeNouveau());
        avancement.setEchellePrecedente(personnel.getEchelleActuelle());
        avancement.setEchelleNouvelle(dto.getEchelleNouvelle());
        avancement.setEchelonPrecedent(personnel.getEchelonActuel());
        avancement.setEchelonNouveau(dto.getEchelonNouveau());
        avancement.setDescription(dto.getDescription());
        avancement.setPersonnel(personnel);
        
        // Update personnel current grade
        personnel.setGradeActuel(dto.getGradeNouveau());
        if (dto.getEchelleNouvelle() != null) {
            personnel.setEchelleActuelle(dto.getEchelleNouvelle());
        }
        if (dto.getEchelonNouveau() != null) {
            personnel.setEchelonActuel(dto.getEchelonNouveau());
        }
        personnelRepository.save(personnel);
        
        avancement = avancementRepository.save(avancement);
        return convertToDTO(avancement);
    }
    
    private AvancementDTO convertToDTO(Avancement avancement) {
        AvancementDTO dto = new AvancementDTO();
        dto.setId(avancement.getId());
        dto.setDateDecision(avancement.getDateDecision());
        dto.setDateEffet(avancement.getDateEffet());
        dto.setGradePrecedent(avancement.getGradePrecedent());
        dto.setGradeNouveau(avancement.getGradeNouveau());
        dto.setEchellePrecedente(avancement.getEchellePrecedente());
        dto.setEchelleNouvelle(avancement.getEchelleNouvelle());
        dto.setEchelonPrecedent(avancement.getEchelonPrecedent());
        dto.setEchelonNouveau(avancement.getEchelonNouveau());
        dto.setDescription(avancement.getDescription());
        dto.setPersonnelId(avancement.getPersonnel().getId());
        return dto;
    }
}

