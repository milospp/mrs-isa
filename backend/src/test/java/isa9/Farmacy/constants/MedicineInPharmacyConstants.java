package isa9.Farmacy.constants;

import isa9.Farmacy.model.MedicineInPharmacy;

public class MedicineInPharmacyConstants {
    public static MedicineInPharmacy getMIP() {
        MedicineInPharmacy medicineInPharmacy = new MedicineInPharmacy();
        medicineInPharmacy.setId(1L);
        medicineInPharmacy.setMedicine(MedicineConstants.getMedicineExample());
        medicineInPharmacy.setPharmacy(PharmacyConstants.getPharmacyExample());
        medicineInPharmacy.setInStock(100);

        return medicineInPharmacy;
    }

    public static MedicineInPharmacy getMIPOutOfStock() {
        MedicineInPharmacy medicineInPharmacy = new MedicineInPharmacy();
        medicineInPharmacy.setId(1L);
        medicineInPharmacy.setMedicine(MedicineConstants.getMedicineExample());
        medicineInPharmacy.setPharmacy(PharmacyConstants.getPharmacyExample());
        medicineInPharmacy.setInStock(0);

        return medicineInPharmacy;
    }


}
