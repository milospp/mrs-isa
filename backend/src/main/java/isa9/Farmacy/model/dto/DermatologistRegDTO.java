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

public class DermatologistRegDTO {
    private Long id;
    private String name, surname, phoneNumber, password, email;
    private Address address;


}
