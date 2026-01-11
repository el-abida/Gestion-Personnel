package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.CitoyenDTO;
import ma.ensate.rh.ensate_rh_backend.dto.PersonnelDTO;
import ma.ensate.rh.ensate_rh_backend.model.Citoyen;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.CitoyenRepository;
import ma.ensate.rh.ensate_rh_backend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonnelService {
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private CitoyenRepository citoyenRepository;
    
    public List<PersonnelDTO> getAllPersonnel() {
        return personnelRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PersonnelDTO getPersonnelById(Long id) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + id));
        return convertToDTO(personnel);
    }
    
    public PersonnelDTO createPersonnel(PersonnelDTO dto) {
        if (personnelRepository.existsByPpr(dto.getPpr())) {
            throw new RuntimeException("PPR already exists: " + dto.getPpr());
        }
        
        if (personnelRepository.existsByCitoyen_Cin(dto.getCitoyen().getCin())) {
            throw new RuntimeException("CIN already exists: " + dto.getCitoyen().getCin());
        }
        
        Citoyen citoyen = convertToCitoyen(dto.getCitoyen());
        citoyen = citoyenRepository.save(citoyen);
        
        Personnel personnel = new Personnel();
        personnel.setCitoyen(citoyen);
        personnel.setPpr(dto.getPpr());
        personnel.setTypeEmploye(dto.getTypeEmploye());
        personnel.setDateRecrutementMinistere(dto.getDateRecrutementMinistere());
        personnel.setDateRecrutementEnsa(dto.getDateRecrutementEnsa());
        personnel.setGradeActuel(dto.getGradeActuel());
        personnel.setEchelleActuelle(dto.getEchelleActuelle());
        personnel.setEchelonActuel(dto.getEchelonActuel());
        personnel.setSoldeConges(dto.getSoldeConges() != null ? dto.getSoldeConges() : 0);
        personnel.setEstActif(dto.getEstActif() != null ? dto.getEstActif() : true);
        
        personnel = personnelRepository.save(personnel);
        return convertToDTO(personnel);
    }
    
    public PersonnelDTO updatePersonnel(Long id, PersonnelDTO dto) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + id));
        
        // Update citoyen
        Citoyen citoyen = personnel.getCitoyen();
        CitoyenDTO citoyenDTO = dto.getCitoyen();
        citoyen.setNomFr(citoyenDTO.getNomFr());
        citoyen.setNomAr(citoyenDTO.getNomAr());
        citoyen.setPrenomFr(citoyenDTO.getPrenomFr());
        citoyen.setPrenomAr(citoyenDTO.getPrenomAr());
        citoyen.setAdresse(citoyenDTO.getAdresse());
        citoyen.setEmail(citoyenDTO.getEmail());
        citoyen.setTelephone(citoyenDTO.getTelephone());
        citoyen.setDateNaissance(citoyenDTO.getDateNaissance());
        citoyen.setLieuNaissance(citoyenDTO.getLieuNaissance());
        citoyen.setSexe(citoyenDTO.getSexe());
        citoyen.setPhotoUrl(citoyenDTO.getPhotoUrl());
        citoyenRepository.save(citoyen);
        
        // Update personnel
        if (dto.getPpr() != null && !dto.getPpr().equals(personnel.getPpr())) {
            if (personnelRepository.existsByPpr(dto.getPpr())) {
                throw new RuntimeException("PPR already exists: " + dto.getPpr());
            }
            personnel.setPpr(dto.getPpr());
        }
        personnel.setTypeEmploye(dto.getTypeEmploye());
        personnel.setDateRecrutementMinistere(dto.getDateRecrutementMinistere());
        personnel.setDateRecrutementEnsa(dto.getDateRecrutementEnsa());
        personnel.setGradeActuel(dto.getGradeActuel());
        personnel.setEchelleActuelle(dto.getEchelleActuelle());
        personnel.setEchelonActuel(dto.getEchelonActuel());
        if (dto.getSoldeConges() != null) {
            personnel.setSoldeConges(dto.getSoldeConges());
        }
        if (dto.getEstActif() != null) {
            personnel.setEstActif(dto.getEstActif());
        }
        
        personnel = personnelRepository.save(personnel);
        return convertToDTO(personnel);
    }
    
    public void deletePersonnel(Long id) {
        if (!personnelRepository.existsById(id)) {
            throw new RuntimeException("Personnel not found with id: " + id);
        }
        personnelRepository.deleteById(id);
    }
    
    private PersonnelDTO convertToDTO(Personnel personnel) {
        PersonnelDTO dto = new PersonnelDTO();
        dto.setId(personnel.getId());
        dto.setCitoyen(convertToCitoyenDTO(personnel.getCitoyen()));
        dto.setPpr(personnel.getPpr());
        dto.setTypeEmploye(personnel.getTypeEmploye());
        dto.setDateRecrutementMinistere(personnel.getDateRecrutementMinistere());
        dto.setDateRecrutementEnsa(personnel.getDateRecrutementEnsa());
        dto.setGradeActuel(personnel.getGradeActuel());
        dto.setEchelleActuelle(personnel.getEchelleActuelle());
        dto.setEchelonActuel(personnel.getEchelonActuel());
        dto.setSoldeConges(personnel.getSoldeConges());
        dto.setEstActif(personnel.getEstActif());
        return dto;
    }
    
    private CitoyenDTO convertToCitoyenDTO(Citoyen citoyen) {
        CitoyenDTO dto = new CitoyenDTO();
        dto.setCin(citoyen.getCin());
        dto.setNomFr(citoyen.getNomFr());
        dto.setNomAr(citoyen.getNomAr());
        dto.setPrenomFr(citoyen.getPrenomFr());
        dto.setPrenomAr(citoyen.getPrenomAr());
        dto.setAdresse(citoyen.getAdresse());
        dto.setEmail(citoyen.getEmail());
        dto.setTelephone(citoyen.getTelephone());
        dto.setDateNaissance(citoyen.getDateNaissance());
        dto.setLieuNaissance(citoyen.getLieuNaissance());
        dto.setSexe(citoyen.getSexe());
        dto.setPhotoUrl(citoyen.getPhotoUrl());
        return dto;
    }
    
    private Citoyen convertToCitoyen(CitoyenDTO dto) {
        Citoyen citoyen = new Citoyen();
        citoyen.setCin(dto.getCin());
        citoyen.setNomFr(dto.getNomFr());
        citoyen.setNomAr(dto.getNomAr());
        citoyen.setPrenomFr(dto.getPrenomFr());
        citoyen.setPrenomAr(dto.getPrenomAr());
        citoyen.setAdresse(dto.getAdresse());
        citoyen.setEmail(dto.getEmail());
        citoyen.setTelephone(dto.getTelephone());
        citoyen.setDateNaissance(dto.getDateNaissance());
        citoyen.setLieuNaissance(dto.getLieuNaissance());
        citoyen.setSexe(dto.getSexe());
        citoyen.setPhotoUrl(dto.getPhotoUrl());
        return citoyen;
    }
}

