package ma.ensate.rh.ensate_rh_backend.service;

import ma.ensate.rh.ensate_rh_backend.dto.AbsenceDTO;
import ma.ensate.rh.ensate_rh_backend.model.Absence;
import ma.ensate.rh.ensate_rh_backend.model.Personnel;
import ma.ensate.rh.ensate_rh_backend.repository.AbsenceRepository;
import ma.ensate.rh.ensate_rh_backend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public AbsenceDTO createAbsence(AbsenceDTO dto) {
        Personnel personnel = personnelRepository.findById(dto.getPersonnelId())
                .orElseThrow(() -> new RuntimeException("Personnel not found with id: " + dto.getPersonnelId()));

        if (dto.getDateDebut().isAfter(dto.getDateFin())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        Absence absence = new Absence();
        absence.setDateDebut(dto.getDateDebut());
        absence.setDateFin(dto.getDateFin());
        absence.setType(dto.getType());
        absence.setMotif(dto.getMotif());
        absence.setJustificatifUrl(dto.getJustificatifUrl());
        absence.setEstValideeParAdmin(dto.getEstValideeParAdmin());
        absence.setPersonnel(personnel);

        absence = absenceRepository.save(absence);
        return convertToDTO(absence);
    }

    public List<AbsenceDTO> getAllAbsences() {
        return absenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AbsenceDTO getAbsenceById(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));
        return convertToDTO(absence);
    }

    public List<AbsenceDTO> getAbsencesByPersonnelId(Long personnelId) {
        return absenceRepository.findByPersonnelId(personnelId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AbsenceDTO updateAbsence(Long id, AbsenceDTO dto) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));

        absence.setDateDebut(dto.getDateDebut());
        absence.setDateFin(dto.getDateFin());
        absence.setType(dto.getType());
        absence.setMotif(dto.getMotif());
        absence.setJustificatifUrl(dto.getJustificatifUrl());

        absence = absenceRepository.save(absence);
        return convertToDTO(absence);
    }

    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }

    public AbsenceDTO validateAbsence(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));

        if (absence.getEstValideeParAdmin()) {
            throw new RuntimeException("Absence already validated");
        }

        absence.setEstValideeParAdmin(true);

        // Deduct leave balance for Annual Leave or Non-Justified Absences
        if (absence.getType() == Absence.TypeAbsence.Congé_Annuel ||
                absence.getType() == Absence.TypeAbsence.Non_Justifiée) {

            Personnel personnel = absence.getPersonnel();
            long days = ChronoUnit.DAYS.between(absence.getDateDebut(), absence.getDateFin()) + 1;
            int newBalance = personnel.getSoldeConges() - (int) days;

            // Allow balance to go slightly negative for non-justified if needed,
            // or keep the check for annual leave only?
            // Usually, we should check for sufficient balance for positive requests.
            if (absence.getType() == Absence.TypeAbsence.Congé_Annuel && newBalance < 0) {
                throw new RuntimeException("Insufficient leave balance");
            }

            personnel.setSoldeConges(Math.max(0, newBalance)); // Prevent extreme negative values if desired
            personnelRepository.save(personnel);
        }

        absence = absenceRepository.save(absence);
        return convertToDTO(absence);
    }

    private AbsenceDTO convertToDTO(Absence absence) {
        AbsenceDTO dto = new AbsenceDTO();
        dto.setId(absence.getId());
        dto.setDateDebut(absence.getDateDebut());
        dto.setDateFin(absence.getDateFin());
        dto.setType(absence.getType());
        dto.setMotif(absence.getMotif());
        dto.setJustificatifUrl(absence.getJustificatifUrl());
        dto.setEstValideeParAdmin(absence.getEstValideeParAdmin());
        dto.setPersonnelId(absence.getPersonnel().getId());

        if (absence.getPersonnel() != null) {
            dto.setPersonnelPpr(absence.getPersonnel().getPpr());
            dto.setPersonnelSolde(absence.getPersonnel().getSoldeConges());
            if (absence.getPersonnel().getCitoyen() != null) {
                dto.setPersonnelNom(absence.getPersonnel().getCitoyen().getNomFr());
                dto.setPersonnelPrenom(absence.getPersonnel().getCitoyen().getPrenomFr());
            }
        }

        return dto;
    }
}
