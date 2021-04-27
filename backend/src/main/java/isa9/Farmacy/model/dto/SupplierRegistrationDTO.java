package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class SupplierRegistrationDTO extends UserDTO{
    private Long id;
    private String name, surname, phoneNumber, password, email;
    private Address address;

    public SupplierRegistrationDTO(Long id, String name, String surname, Address address,
                                   String phoneNumber, RolesDTO role, String email, String password) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.address = address;
    }


}
