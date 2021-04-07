package isa9.Farmacy.service;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Pharmacy;

public interface MedReservationService extends GenericService<MedReservation> {
    Boolean isCancelable(MedReservation medReservation);
    MedReservation cancel(Long medReservationId);
}
