package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class PharmacistDTO extends DoctorDTO {

    private Pharmacy pharmacy;
    //private Long id;

    @Deprecated
    public PharmacistDTO(Long id, String name, String surname, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, address, phoneNumber, RolesDTO.PHARMACIST, null);
        this.pharmacy = pharmacy;
    }

    public PharmacistDTO(Long id, String name, String surname, Address address, String phoneNumber, RolesDTO role, String email, Pharmacy pharmacy) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.pharmacy = pharmacy;
    }

    public PharmacistDTO(Long id, String name, String surname, Address address, String phoneNumber, String email, Pharmacy pharmacy) {
        super(id, name, surname, address, phoneNumber, RolesDTO.PHARMACIST, email);
        this.pharmacy = pharmacy;
    }
}
