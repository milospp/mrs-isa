package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EqualsAndHashCode
public class MedPrice {
    // za svaku promenu cene, pravi se nova klasa cene
    @Id
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private double price;
    @ManyToOne
    private Medicine medicine;
    @ManyToOne
    private Pharmacy pharmacy;

    public MedPrice() {

    }

    public MedPrice(Long id, LocalDateTime startDate, double price, Medicine medicine, Pharmacy pharmacy) {
        this.startDate = startDate;
        this.price = price;
        this.medicine = medicine;
        this.pharmacy = pharmacy;
        this.id = id;
    }

    @Override
    public String toString() {
        return "MedPrice{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", price=" + price +
                ", medicine=" + medicine +
                ", pharmacy=" + pharmacy +
                '}';
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
