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
@Builder

public class PharmacistDTO extends DoctorDTO {

    private Pharmacy pharmacy;
    //private Long id;



    public PharmacistDTO(Long id, String name, String surname, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, address, phoneNumber, UserRole.PHARMACIST);
        this.pharmacy = pharmacy;
    }

}
