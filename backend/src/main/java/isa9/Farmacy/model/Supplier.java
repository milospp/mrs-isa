package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

@Entity
public class Supplier extends User{
    @OneToMany
    private List<Offer> allOffer;

    @OneToMany
    private List<MedicineQuantity> quantityOnState;

    public Supplier() {
    }


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

    public List<Offer> getAllOffer() {
        return allOffer;
    }

    public void setAllOffer(List<Offer> allOffer) {
        this.allOffer = allOffer;
    }

    public List<MedicineQuantity> getQuantityOnState() {
        return quantityOnState;
    }

    public void setQuantityOnState(List<MedicineQuantity> quantityOnState) {
        this.quantityOnState = quantityOnState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(allOffer, supplier.allOffer) && Objects.equals(quantityOnState, supplier.quantityOnState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allOffer, quantityOnState);
    }

    @Override
    public String toString() {
        return super.toString() + " Supplier{" +
                "allOffers=" + allOffer +
                ", quantityOnState=" + quantityOnState +
                '}';
    }
}
