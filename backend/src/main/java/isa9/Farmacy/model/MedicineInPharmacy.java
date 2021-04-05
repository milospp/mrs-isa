package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@EqualsAndHashCode
public class MedicineInPharmacy {
    @Id
    private String id;
    @OneToOne
    private MedPrice currentPrice;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int inStock;
    @ManyToOne
    private Pharmacy pharmacy; // mozda ne treba

    public MedicineInPharmacy() {

    }

    public MedicineInPharmacy(String id, MedPrice currentPrice, Medicine medicine, int inStock, Pharmacy pharmacy) {
        this.currentPrice = currentPrice;
        this.medicine = medicine;
        this.inStock = inStock;
        this.pharmacy = pharmacy;
        this.id = id;
    }

    @Override
    public String toString() {
        return "MedicineInPharmacy{" +
                "currentPrice=" + currentPrice +
                ", medicine=" + medicine +
                ", inStock=" + inStock +
                ", pharmacy=" + pharmacy +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MedPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(MedPrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
