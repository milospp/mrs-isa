package isa9.Farmacy.service;

import isa9.Farmacy.model.MedicineAtSupplier;
import isa9.Farmacy.model.MedicineQuantity;

import java.util.List;

public interface MedQuantityService extends GenericService<MedicineQuantity> {
    void delete(MedicineQuantity medicineQuantity);


}
