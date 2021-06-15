package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationSearchDTO;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.MedReservationServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
@Service
public class dbMedReservationService extends MedReservationServiceBase implements MedReservationService {

    private final MedReservationRepository medReservationRepository;

    @Autowired
    public dbMedReservationService(MedReservationRepository medReservationRepository) {
        this.medReservationRepository = medReservationRepository;
    }

    @Override
    public List<MedReservation> findAll() {
        return this.medReservationRepository.findAll();
    }

    @Override
    public MedReservation findOne(Long id) {
        return this.medReservationRepository.findById(id).orElse(null);
    }

    @Override
    public MedReservation save(MedReservation entity) {
        return this.medReservationRepository.save(entity);
    }

    @Override
    public Collection<MedReservation> saveAll(Collection<MedReservation> entity) {
        return this.medReservationRepository.saveAll(entity);
    }

    @Override
    public MedReservation getByCode(String code) {
        return medReservationRepository.findByCode(code);
    }

    @Override
    public Collection<MedReservation> filterMedReservations(Collection<MedReservation> medicines, MedReservationSearchDTO medReservationSearchDTO) {
        if (medReservationSearchDTO == null) return medicines;
        final Map<String, Comparator<MedReservation>> critMap = new HashMap<String, Comparator<MedReservation>>();
        critMap.put("NAME_ASC", (o1,o2)->{return o1.getMedicineInPharmacy().getMedicine().getName().trim().compareToIgnoreCase(o2.getMedicineInPharmacy().getMedicine().getName().trim()); });
        critMap.put("NAME_DES", (o2,o1)->{return o1.getMedicineInPharmacy().getMedicine().getName().trim().compareToIgnoreCase(o2.getMedicineInPharmacy().getMedicine().getName().trim()); });
        critMap.put("RES_DATE_ASC", Comparator.comparing(MedReservation::getReservationDate));
        critMap.put("RES_DATE_DES", Comparator.comparing(MedReservation::getReservationDate).reversed());
        critMap.put("LAST_DATE_ASC", Comparator.comparing(MedReservation::getLastDate));
        critMap.put("LAST_DATE_DES", Comparator.comparing(MedReservation::getLastDate).reversed());
        critMap.put("QUANTITY_ASC", Comparator.comparingDouble(MedReservation::getQuantity));
        critMap.put("QUANTITY_DES", Comparator.comparingDouble(MedReservation::getQuantity).reversed());
        Comparator<MedReservation> comp = critMap.getOrDefault(medReservationSearchDTO.getSort().toUpperCase().trim(), critMap.values().iterator().next());
        return medicines.stream()
                .filter(m -> medReservationSearchDTO.getName().isEmpty() || m.getMedicineInPharmacy().getMedicine().getName().toLowerCase().contains(medReservationSearchDTO.getName().toLowerCase()))
                .filter(m -> medReservationSearchDTO.getPharmacy().isEmpty() || m.getMedicineInPharmacy().getPharmacy().getName().toLowerCase().contains(medReservationSearchDTO.getPharmacy().toLowerCase()))
                .filter(m -> medReservationSearchDTO.getMaxResDate() == null || !m.getReservationDate().isAfter(medReservationSearchDTO.getMaxResDate()))
                .filter(m -> medReservationSearchDTO.getMinResDate() == null || !m.getReservationDate().isBefore(medReservationSearchDTO.getMinResDate()))
                .filter(m -> medReservationSearchDTO.getMaxLastDate() == null || !m.getLastDate().isAfter(medReservationSearchDTO.getMaxLastDate()))
                .filter(m -> medReservationSearchDTO.getMinLastDate() == null || !m.getLastDate().isBefore(medReservationSearchDTO.getMinLastDate()))

                .filter(m -> m.getQuantity() >= medReservationSearchDTO.getMinQuantity() && m.getQuantity() <= medReservationSearchDTO.getMaxQuantity())
                .filter(m -> medReservationSearchDTO.isOnlye() == false || m.getCode().startsWith("ePres") )
                .sorted(comp).collect(Collectors.toList());
    }

    @Override
    @Transactional
    // TODO dodatni konflikt resen
    public MedReservation getByCodeLocked(String code) {
        return medReservationRepository.findByCodeLocked(code);
    }

    @Override
    @Scheduled(cron="0 0 0 * * ?") // runs every day at midnight
    public void checkForExpiredReservations() {

        Collection<MedReservation> outdatedReservations = medReservationRepository.findAllByStatusAndLastDateBefore(MedReservationStatus.PENDING, LocalDate.now());

        for (MedReservation reservation : outdatedReservations){
            reservation.setStatus(MedReservationStatus.EXPIRED);
            userService.addPenalty(reservation.getPatient(), "Patient did not pick up reserved medication. #"+reservation.getCode());
        }

        saveAll(outdatedReservations);

        System.out.println("Number of expired reservations: " + outdatedReservations.size() + " --- on date: " + LocalDate.now());
    }

    @Override
    public Boolean patientConsumedMedicine(Patient patient, Medicine medicine) {
        MedReservation medReservation;
        medReservation = medReservationRepository.findFirstByPatientAndMedicineInPharmacy_MedicineAndStatus(patient, medicine, MedReservationStatus.TAKEN);
        if (medReservation == null) return false;
        else return true;
    }

    @Override
    public Boolean patientConsumedMedInPharmacy(Patient patient, Pharmacy pharmacy) {
        MedReservation medReservation;
        medReservation = medReservationRepository.findFirstByPatientAndMedicineInPharmacy_PharmacyAndStatus(patient, pharmacy, MedReservationStatus.TAKEN);
        if (medReservation == null) return false;
        else return true;
    }

}
