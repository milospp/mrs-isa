package isa9.Farmacy.constants;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Pharmacy;

public class PharmacyConstants {
    public static Pharmacy getPharmacyExample() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        pharmacy.setName("Name1");
        pharmacy.setAddress(new Address("Street", "Number", "City", "State", 45.2421291534108, 19.84261570497869));
        pharmacy.setDescription("Desc");
        pharmacy.setPricePerHour(500);
        return pharmacy;
    }
}
