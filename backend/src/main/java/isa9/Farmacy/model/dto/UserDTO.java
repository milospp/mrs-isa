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

public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private Address address; // pretvori se u string?
    private String phoneNumber;
    private UserRole role;
    private String email;


    @Deprecated
    public UserDTO(Long id, String name, String surname, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = new Address();
        this.phoneNumber = phoneNumber;
    }

    @Deprecated
    public UserDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }




}
