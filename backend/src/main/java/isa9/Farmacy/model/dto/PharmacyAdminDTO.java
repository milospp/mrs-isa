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

public class PharmacyAdminDTO extends UserDTO{
    private Long pharmacyId;


    @Deprecated
    public PharmacyAdminDTO(Long id, String name, String surname, Address address,
                            String phoneNumber, Long pharmacyId) {
        super(id, name, surname, address, phoneNumber, UserRole.PHARMACY_ADMIN, null);
        this.pharmacyId = pharmacyId;
    }

    public PharmacyAdminDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole role, String email, Long pharmacyId) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.pharmacyId = pharmacyId;
    }
}
