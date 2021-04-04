package isa9.Farmacy.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class MedPrice {
    // za svaku promenu cene, pravi se nova klasa cene
    private Long id;
    private LocalDateTime startDate;
    private double price;
    private Medicine medicine;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedPrice medPrice = (MedPrice) o;
        return id.equals(medPrice.id) && Double.compare(medPrice.price, price) == 0 && startDate.equals(medPrice.startDate) && medicine.equals(medPrice.medicine) && pharmacy.equals(medPrice.pharmacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, price, medicine, pharmacy);
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
