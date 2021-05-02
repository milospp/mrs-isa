package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

public class SupplierDTO extends UserDTO{
    public SupplierDTO(Long id, String name, String surname, Address address, String phoneNumber, RolesDTO role, String email) {
        super(id, name, surname, address, phoneNumber, role, email);
    }
}
