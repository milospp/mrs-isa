package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.UserRole;

public class PharmacistDTO extends DoctorDTO {

    private Pharmacy pharmacy;

    public PharmacistDTO() {
    }

    public PharmacistDTO(Long id, String name, String surname, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, address, phoneNumber, UserRole.PHARMACIST);
        this.pharmacy = pharmacy;
    }
    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
