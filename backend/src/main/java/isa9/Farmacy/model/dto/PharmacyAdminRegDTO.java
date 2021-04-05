package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PharmacyAdminRegDTO {
    private String name, surname, email, password, phoneNumber;
    private Address address;
    private Long pharmacyId;

}
