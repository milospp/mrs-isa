package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class OrderOffer {
    private double price;
    private boolean acception;
    private Date deliveryDate;
    private MedicineOrder medicineOrder;

    public OrderOffer() {
        super();
    }

    public OrderOffer(double price, boolean acception, Date deliveryDate, MedicineOrder medicineOrder) {
        super();
        this.price = price;
        this.acception = acception;
        this.deliveryDate = deliveryDate;
        this.medicineOrder = medicineOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAcception() {
        return acception;
    }

    public void setAcception(boolean acception) {
        this.acception = acception;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public MedicineOrder getMedicineOrder() {
        return medicineOrder;
    }

    public void setMedicineOrder(MedicineOrder medicineOrder) {
        this.medicineOrder = medicineOrder;
    }

    @Override
    public String toString() {
        return "OrderOffer [price=" + price + ", acception=" + acception + ", deliveryDate=" + deliveryDate
                + ", medicineOrder=" + medicineOrder + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderOffer that = (OrderOffer) o;
        return Double.compare(that.price, price) == 0 && acception == that.acception && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(medicineOrder, that.medicineOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, acception, deliveryDate, medicineOrder);
    }
}
