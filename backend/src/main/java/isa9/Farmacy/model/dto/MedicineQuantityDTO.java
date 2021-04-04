package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;

import java.util.Objects;

public class MedicineQuantityDTO {
    private MedicineDTO medicine;
    private int quantity;

    public MedicineQuantityDTO() {
    }

    public MedicineQuantityDTO(MedicineDTO medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
