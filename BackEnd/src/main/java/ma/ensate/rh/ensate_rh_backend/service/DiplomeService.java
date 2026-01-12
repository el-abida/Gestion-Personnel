package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.DiplomeDTO;
import ma.ensate.rh.ensate_rh_backend.model.Diplome;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.DiplomeRepository;
import ma.ensate.rh.ensate_rh_backend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiplomeService {

    @Autowired
    private DiplomeRepository diplomeRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public List<DiplomeDTO> getAllDiplomes() {
        return diplomeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DiplomeDTO> getDiplomesByPersonnelId(Long personnelId) {
        return diplomeRepository.findByPersonnelId(personnelId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DiplomeDTO getDiplomeById(Long id) {
        Diplome diplome = diplomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplome not found with id: " + id));
        return convertToDTO(diplome);
    }

    public DiplomeDTO createDiplome(DiplomeDTO dto) {
        Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));

        Diplome diplome = new Diplome();
        diplome.setIntitule(dto.getIntitule());
        diplome.setSpecialite(dto.getSpecialite());
        diplome.setNiveau(dto.getNiveau());
        diplome.setEtablissement(dto.getEtablissement());
        diplome.setDateObtention(dto.getDateObtention());
        diplome.setFichierPreuve(dto.getFichierPreuve());
        diplome.setPersonnel(personnel);

        diplome = diplomeRepository.save(diplome);
        return convertToDTO(diplome);
    }

    public DiplomeDTO updateDiplome(Long id, DiplomeDTO dto) {
        Diplome diplome = diplomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplome not found with id: " + id));

        diplome.setIntitule(dto.getIntitule());
        diplome.setSpecialite(dto.getSpecialite());
        diplome.setNiveau(dto.getNiveau());
        diplome.setEtablissement(dto.getEtablissement());
        diplome.setDateObtention(dto.getDateObtention());
        diplome.setFichierPreuve(dto.getFichierPreuve());

        if (dto.getPersonnelId() != null && !dto.getPersonnelId().equals(diplome.getPersonnel().getId())) {
            Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                    .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));
            diplome.setPersonnel(personnel);
        }

        diplome = diplomeRepository.save(diplome);
        return convertToDTO(diplome);
    }

    public void deleteDiplome(Long id) {
        if (!diplomeRepository.existsById(id)) {
            throw new RuntimeException("Diplome not found with id: " + id);
        }
        diplomeRepository.deleteById(id);
    }

    private DiplomeDTO convertToDTO(Diplome diplome) {
        DiplomeDTO dto = new DiplomeDTO();
        dto.setId(diplome.getId());
        dto.setIntitule(diplome.getIntitule());
        dto.setSpecialite(diplome.getSpecialite());
        dto.setNiveau(diplome.getNiveau());
        dto.setEtablissement(diplome.getEtablissement());
        dto.setDateObtention(diplome.getDateObtention());
        dto.setFichierPreuve(diplome.getFichierPreuve());

        if (diplome.getPersonnel() != null) {
            dto.setPersonnelId(diplome.getPersonnel().getId());
            if (diplome.getPersonnel().getCitoyen() != null) {
                dto.setPersonnelNom(diplome.getPersonnel().getCitoyen().getNomFr());
                dto.setPersonnelPrenom(diplome.getPersonnel().getCitoyen().getPrenomFr());
            }
        }

        return dto;
    }
}
