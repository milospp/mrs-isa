package isa9.Farmacy.service;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PharmacySearchDTO;

import java.util.List;

public interface PharmacyService extends GenericService<Pharmacy> {

    boolean pharmacyExists(Pharmacy p);
    boolean reduceQuantity(Pharmacy pharmacy, Medicine medicine, int resQuantity);


    MedicineInPharmacy gedMedicineInPharmacy(Pharmacy pharmacy, Medicine medicine);

    List<Work> findDoctorsWork(Doctor doctor);

    Pharmacy updatePharmacyRating(Pharmacy pharmacy);

    List<Pharmacy> filterPharmacies(List<Pharmacy> pharmacies, PharmacySearchDTO pharmacySearchDTO);
}
