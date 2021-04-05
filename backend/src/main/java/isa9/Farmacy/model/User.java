package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String password;

    @OneToOne
    private Address address;

    @Column
    private String phoneNumber;

    @Column
    @Enumerated
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
