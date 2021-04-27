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

public class PatientRegistrationDTO {
    private Long id;
    private String name, surname, phoneNumber, password, email;
    private Address address;


    public PatientRegistrationDTO(Long id, String name, String surname,
                                  Address address, String phoneNumber, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }


}
