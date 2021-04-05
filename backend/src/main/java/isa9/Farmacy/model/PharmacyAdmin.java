package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class PharmacyAdmin extends User{

    @ManyToOne
    private Pharmacy pharmacy;

    public PharmacyAdmin(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, UserRole role, Pharmacy pharmacy) {
        super(id, name, surname, email, password, address, phoneNumber, role);
        this.pharmacy = pharmacy;
    }

    public PharmacyAdmin(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.PHARMACY_ADMIN);
        this.pharmacy = pharmacy;
    }
}
