package isa9.Farmacy.service;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.EPrescriptionDTO;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.model.dto.MedReservationSearchDTO;
import isa9.Farmacy.model.dto.MedicineSearchDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MedReservationService extends GenericService<MedReservation> {

    MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO, Long doctorId);
    MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO);

    Collection<MedReservation> filterMedReservations(Collection<MedReservation> medicines, MedReservationSearchDTO medReservationSearchDTO);


    Collection<MedReservation> saveAll(Collection<MedReservation> entity);

    Boolean isCancelable(MedReservation medReservation);
    MedReservation cancel(Long medReservationId);

    MedReservation getByCode(String code);
    MedReservation getByCodeLocked(String code);
    MedReservation dispenseMedicine(String code);

    Boolean patientConsumedMedicine(Patient patient, Medicine medicine);
    Boolean patientConsumedMedInPharmacy(Patient patient, Pharmacy pharmacy);

    List<MedReservation> getPatientsPurchases(Patient patient);

    void checkForExpiredReservations();

    Integer eReserveMedicines(EPrescriptionDTO ePrescriptionDTO);
}
