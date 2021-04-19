package isa9.Farmacy.service;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.MedReservationFormDTO;

public interface MedReservationService extends GenericService<MedReservation> {
    MedReservation reserveMedicine(MedReservationFormDTO reservationFormDTO);

    Boolean isCancelable(MedReservation medReservation);
    MedReservation cancel(Long medReservationId);

    MedReservation getByCode(String code);

    void checkForExpiredReservations();
}
