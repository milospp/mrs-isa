package isa9.Farmacy.service;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.MedReservationFormDTO;

public interface MedReservationService extends GenericService<MedReservation> {
    MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO);

    Boolean isCancelable(MedReservation medReservation);
    MedReservation cancel(Long medReservationId);

    MedReservation getByCode(String code);
    MedReservation dispenseMedicine(MedReservation medReservation);

    Boolean patientConsumedMedicine(Patient patient, Medicine medicine);
    Boolean patientConsumedMedInPharmacy(Patient patient, Pharmacy pharmacy);


    void checkForExpiredReservations();
}
