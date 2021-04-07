package isa9.Farmacy.service;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.Pharmacy;

public interface PharmacyService extends GenericService<Pharmacy> {

    boolean pharmacyExists(Pharmacy p);

    boolean reduceQuantity(Pharmacy pharmacy, Medicine medicine, int resQuantity);

    MedicineInPharmacy gedMedicineInPharmacy(Pharmacy pharmacy, Medicine medicine);

}
