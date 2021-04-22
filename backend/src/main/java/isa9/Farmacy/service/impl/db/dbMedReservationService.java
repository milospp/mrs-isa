package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.MedReservationRepository;
import isa9.Farmacy.repository.PharmacyRepository;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.impl.base.MedReservationServiceBase;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        return this.medReservationRepository.findById(id).orElseGet(null);
    }

    @Override
    public MedReservation save(MedReservation entity) {
        return this.medReservationRepository.save(entity);
    }

    @Override
    public MedReservation getByCode(String code) {
        return medReservationRepository.findByCode(code);
    }

    @Override
    @Scheduled(cron="0 0 0 * * ?") // runs every day at midnight
    public void checkForExpiredReservations() {
        int count = 0;
        for (MedReservation mr : this.findAll()){
            if (mr.getLastDate().isBefore(LocalDate.now().minusDays(1)) && mr.getStatus() == MedReservationStatus.PENDING){
                mr.setStatus(MedReservationStatus.EXPIRED);
                count++;
            }
        }
        System.out.println("Number of expired reservations: " + count + " --- on date: " + LocalDate.now());
    }

    @Override
    public Boolean patientConsumedMedicine(Patient patient, Medicine medicine) {
        MedReservation medReservation;
        medReservation = medReservationRepository.findFirstByPatientAndMedicineInPharmacy_MedicineAndStatus(patient, medicine, MedReservationStatus.TAKEN);
        if (medReservation == null) return false;
        else return true;
    }
}
