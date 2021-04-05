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
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Supplier extends User{
    @OneToMany
    private List<Offer> allOffer;

    @OneToMany
    private List<MedicineQuantity> quantityOnState;



    public Supplier(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.SUPPLIER);
        this.allOffer = new ArrayList<Offer>();
        this.quantityOnState = new ArrayList<MedicineQuantity>();
    }

    public Supplier(Long id, String name, String surname, String email, String password, Address address,
                    String phoneNumber, List<Offer> allOffer, List<MedicineQuantity> quantityOnState) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.SUPPLIER);
        this.allOffer = allOffer;
        this.quantityOnState = quantityOnState;
    }




}
