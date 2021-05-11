package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.MedReservationServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@Primary
@Service
public class dbMedReservationService extends MedReservationServiceBase implements MedReservationService {

    private final MedReservationRepository medReservationRepository;
    private final UserService userService;

    @Autowired
    public dbMedReservationService(MedReservationRepository medReservationRepository, UserService userService) {
        this.medReservationRepository = medReservationRepository;
        this.userService = userService;
    }

    @Override
    public List<MedReservation> findAll() {
        return this.medReservationRepository.findAll();
    }

    @Override
    public MedReservation findOne(Long id) {
        return this.medReservationRepository.findById(id).orElseGet(null);
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
        else return true;    }

}
