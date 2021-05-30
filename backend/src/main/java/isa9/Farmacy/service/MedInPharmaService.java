package isa9.Farmacy.service;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;

import java.util.Collection;

public interface MedInPharmaService extends GenericService<MedicineInPharmacy> {
    Collection<MedicineInPharmacy> findAllMedicinesInPharmacy(Long medicineId);
    void checkForExpiredActionOrPromotion();       // kada istekne akcija ili promocija vrati staru cenu
}
