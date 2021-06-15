package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface MedReservationRepository extends JpaRepository<MedReservation, Long> {
    MedReservation findFirstByPatientAndMedicineInPharmacy_MedicineAndStatus(Patient patient, Medicine medicine, MedReservationStatus status);
    MedReservation findByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT r FROM MedReservation r WHERE r.code = :code")
    MedReservation findByCodeLocked(@Param("code") String code);

    MedReservation findFirstByPatientAndMedicineInPharmacy_PharmacyAndStatus(Patient patient, Pharmacy pharmacy, MedReservationStatus status);
    Collection<MedReservation> findAllByStatusAndLastDateBefore(MedReservationStatus status, LocalDate lastDate);
}
