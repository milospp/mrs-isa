package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.service.MedQuantityService;

public abstract class MedQuantityServiceBase implements MedQuantityService {
    @Override
    public void reduceQuantity(MedicineQuantity medicineQuantity, int quantity) {
        medicineQuantity.setQuantity(medicineQuantity.getQuantity() - quantity);
        this.save(medicineQuantity);
    }
}
