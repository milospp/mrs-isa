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
@Builder

public class PharmacyAdminDTO extends UserDTO{
    private Long pharmacyId;



    public PharmacyAdminDTO(Long id, String name, String surname, Address address,
                            String phoneNumber, Long pharmacyId) {
        super(id, name, surname, address, phoneNumber, UserRole.PHARMACY_ADMIN);
        this.pharmacyId = pharmacyId;
    }


}
