package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Supplier extends User{
    @OneToMany
    private List<Offer> allOffer;

    @OneToMany
    private List<MedicineQuantity> quantityOnState;



    public Supplier(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, UserRole role) {
        super(id, name, surname, email, password, address, phoneNumber, role, true);
        this.allOffer = new ArrayList<Offer>();
        this.quantityOnState = new ArrayList<MedicineQuantity>();
    }






}
