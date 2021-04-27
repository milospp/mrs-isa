package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedReservationRepository extends JpaRepository<MedReservation, Long> {
    MedReservation findFirstByPatientAndMedicineInPharmacy_MedicineAndStatus(Patient patient, Medicine medicine, MedReservationStatus status);
    MedReservation findByCode(String code);

    MedReservation findFirstByPatientAndMedicineInPharmacy_PharmacyAndStatus(Patient patient, Pharmacy pharmacy, MedReservationStatus status);

}
