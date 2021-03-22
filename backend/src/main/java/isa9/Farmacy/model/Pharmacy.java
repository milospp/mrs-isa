package isa9.Farmacy.model;

import java.util.Objects;

public class Pharmacy {
    private String name;
    private Address address;
    private String description;
    private String id;
    //private List<MedicineStockPrice> medicineStockPrices; // ?

    public Pharmacy() {
        super();
    }

    public Pharmacy(String name, Address address, String description, String id/*,
                    List<MedicineStockPrice> medicineStockPrices*/) {
        super();
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        //this.medicineStockPrices = medicineStockPrices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
/*
    public List<MedicineStockPrice> getMedicineStockPrices() {
        return medicineStockPrices;
    }

    public void setMedicineStockPrices(List<MedicineStockPrice> medicineStockPrices) {
        this.medicineStockPrices = medicineStockPrices;
    }*/

    @Override
    public String toString() {
        return "Pharmacy [name=" + name + ", address=" + address + ", description=" + description + ", id=" + id
                + ", medicineStockPrices="/* + medicineStockPrices */+ "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(name, pharmacy.name) && Objects.equals(address, pharmacy.address) && Objects.equals(description, pharmacy.description) && Objects.equals(id, pharmacy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, description, id);
    }
}
