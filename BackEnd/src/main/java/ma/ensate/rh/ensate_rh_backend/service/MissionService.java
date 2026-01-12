package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.MissionDTO;
import ma.ensate.rh.ensate_rh_backend.model.Mission;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.MissionRepository;
import ma.ensate.rh.ensate_rh_backend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public MissionDTO createMission(MissionDTO dto) {
        Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));

        if (dto.getDateRetour() != null && dto.getDateDepart().isAfter(dto.getDateRetour())) {
            throw new RuntimeException("Departure date cannot be after return date");
        }

        Mission mission = new Mission();
        mission.setDestination(dto.getDestination());
        mission.setObjetMission(dto.getObjetMission());
        mission.setDateDepart(dto.getDateDepart());
        mission.setDateRetour(dto.getDateRetour());
        mission.setStatut(dto.getStatut() != null ? dto.getStatut() : Mission.StatutMission.Planifiée);
        mission.setRapportUrl(dto.getRapportUrl());
        mission.setPersonnel(personnel);

        mission = missionRepository.save(mission);
        return convertToDTO(mission);
    }

    public List<MissionDTO> getMissionsByPersonnelId(Long personnelId) {
        return missionRepository.findByPersonnelId(personnelId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MissionDTO> getAllMissions() {
        return missionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MissionDTO getMissionById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + id));
        return convertToDTO(mission);
    }

    public MissionDTO updateMission(Long id, MissionDTO dto) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + id));

        if (dto.getDateRetour() != null && dto.getDateDepart().isAfter(dto.getDateRetour())) {
            throw new RuntimeException("Departure date cannot be after return date");
        }

        mission.setDestination(dto.getDestination());
        mission.setObjetMission(dto.getObjetMission());
        mission.setDateDepart(dto.getDateDepart());
        mission.setDateRetour(dto.getDateRetour());
        if (dto.getStatut() != null) {
            mission.setStatut(dto.getStatut());
        }
        mission.setRapportUrl(dto.getRapportUrl());

        if (dto.getPersonnelId() != null && !dto.getPersonnelId().equals(mission.getPersonnel().getId())) {
            Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                    .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));
            mission.setPersonnel(personnel);
        }

        mission = missionRepository.save(mission);
        return convertToDTO(mission);
    }

    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new RuntimeException("Mission not found with id: " + id);
        }
        missionRepository.deleteById(id);
    }

    public MissionDTO closeMission(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + id));

        if (mission.getStatut() == Mission.StatutMission.Terminée) {
            throw new RuntimeException("Mission already closed");
        }

        mission.setStatut(Mission.StatutMission.Terminée);
        mission = missionRepository.save(mission);
        return convertToDTO(mission);
    }

    private MissionDTO convertToDTO(Mission mission) {
        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setDestination(mission.getDestination());
        dto.setObjetMission(mission.getObjetMission());
        dto.setDateDepart(mission.getDateDepart());
        dto.setDateRetour(mission.getDateRetour());
        dto.setStatut(mission.getStatut());
        dto.setRapportUrl(mission.getRapportUrl());

        if (mission.getPersonnel() != null) {
            dto.setPersonnelId(mission.getPersonnel().getId());
            if (mission.getPersonnel().getCitoyen() != null) {
                dto.setPersonnelNom(mission.getPersonnel().getCitoyen().getNomFr());
                dto.setPersonnelPrenom(mission.getPersonnel().getCitoyen().getPrenomFr());
            }
        }

        return dto;
    }
}
