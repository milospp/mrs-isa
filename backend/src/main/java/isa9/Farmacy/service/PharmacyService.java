package isa9.Farmacy.service;

import isa9.Farmacy.model.*;

import java.util.List;

public interface PharmacyService extends GenericService<Pharmacy> {

    boolean pharmacyExists(Pharmacy p);

    boolean reduceQuantity(Pharmacy pharmacy, Medicine medicine, int resQuantity);

    MedicineInPharmacy gedMedicineInPharmacy(Pharmacy pharmacy, Medicine medicine);

    List<Work> findDoctorsWork(Doctor doctor);

}
