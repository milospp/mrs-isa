package isa9.Farmacy.model;

import java.util.Objects;

public class MedicineInPharmacy {
    private String id;
    private MedPrice currentPrice;
    private Medicine medicine;
    private int inStock;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineInPharmacy that = (MedicineInPharmacy) o;
        return id.equals(that.id) && inStock == that.inStock && currentPrice.equals(that.currentPrice) && medicine.equals(that.medicine) && pharmacy.equals(that.pharmacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentPrice, medicine, inStock, pharmacy);
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
