package isa9.Farmacy.service;

import isa9.Farmacy.model.MedicineAtSupplier;

import java.util.List;
import java.util.Set;

public interface MedicineAtSupplierService extends GenericService<MedicineAtSupplier>{

    public Set<MedicineAtSupplier> medicinesOfSupplier(long id);
    public void updateMedicineAtSupplier(MedicineAtSupplier mas, double newPrice, int newQuantity);
}
