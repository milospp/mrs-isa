package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.UserRole;

public class DoctorDTO extends UserDTO {

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String name, String surname, String address, String phoneNumber) {
        super(id, name, surname, address, phoneNumber);
    }

    public DoctorDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole uloga) {
        super(id, name, surname, address, phoneNumber, uloga);
    }
}
