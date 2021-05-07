package isa9.Farmacy.service;

import isa9.Farmacy.model.MedicineQuantity;

public interface MedQuantityService extends GenericService<MedicineQuantity> {
    void delete(MedicineQuantity medicineQuantity);
}
