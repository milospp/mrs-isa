package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class DermatologistDTO extends DoctorDTO {

    private List<Pharmacy> pharmacies;

    public DermatologistDTO() {
    }

    public DermatologistDTO(Long id, String name, String surname, Address address, String phoneNumber) {
        super(id, name, surname, address, phoneNumber, UserRole.DERMATOLOGIST);
        this.pharmacies = new ArrayList<Pharmacy>();
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }
}
