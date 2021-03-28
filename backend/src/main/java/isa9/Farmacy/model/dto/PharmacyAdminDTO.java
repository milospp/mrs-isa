package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.UserRole;

public class PharmacyAdminDTO extends UserDTO{
    private Long pharmacyId;

    public Long getPharmacyId() { return pharmacyId; }

    public void setPharmacyId(Long pharmacyId) { this.pharmacyId = pharmacyId; }

    public PharmacyAdminDTO(Long id, String name, String surname, Address address,
                            String phoneNumber, Long pharmacyId) {
        super(id, name, surname, address, phoneNumber, UserRole.PHARMACY_ADMIN);
        this.pharmacyId = pharmacyId;
    }

    public PharmacyAdminDTO(){
        super();
    }
}
