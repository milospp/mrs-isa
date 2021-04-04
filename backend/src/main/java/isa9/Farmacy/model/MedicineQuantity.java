package isa9.Farmacy.model;
// klasa za lekove koji su poruceni u narudzbenici

import java.util.Objects;

public class MedicineQuantity {
    private Medicine medicine;
    private int quantity;

    public MedicineQuantity() {
    }

    public MedicineQuantity(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineQuantity orderItem = (MedicineQuantity) o;
        return quantity == orderItem.quantity && Objects.equals(medicine, orderItem.medicine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicine, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "medicine=" + medicine +
                ", quantity=" + quantity +
                '}';
    }
}