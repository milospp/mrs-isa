package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.MedPrice;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Pharmacy;

public class MedInPharmaDTO {
    private String id;
    private double currentPrice;
    private MedicineDTO medicine;
    private int inStock;
    private Long pharmacyId;

    public MedInPharmaDTO() {

    }

    public MedInPharmaDTO(String id, double currentPrice, MedicineDTO medicine, int inStock, Long pharmacyId) {
        this.id = id;
        this.currentPrice = currentPrice;
        this.medicine = medicine;
        this.inStock = inStock;
        this.pharmacyId = pharmacyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
