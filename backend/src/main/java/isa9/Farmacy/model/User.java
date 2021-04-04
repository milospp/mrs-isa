package isa9.Farmacy.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Address address;
    private String phoneNumber;
    private UserRole role;

    @Deprecated
    public User(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

}
