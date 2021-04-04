package isa9.Farmacy.model;

import org.springframework.core.annotation.Order;

import java.time.LocalTime;
import java.util.*;

public class Pharmacy {
    private String name;
    private Address address;
    private String description;
    private Long id;
    private List<Work> staff;
    private List<MedicineOrder> orders;
    private List<MedicineInPharmacy> medicines;

    public Pharmacy() {
        super();
        this.orders = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.medicines = new ArrayList<>();
    }

    public Pharmacy(String name, Address address, String description, Long id) {
        super();
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        this.staff = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.medicines = new ArrayList<>();
    }

    public Pharmacy(String name, Address address, String description, Long id, List<Work> staff, List<MedicineOrder> orders, List<MedicineInPharmacy> medicines) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        this.staff = staff;
        this.orders = orders;
        this.medicines = medicines;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Work> getStaff() {
        return staff;
    }

    public void setStaff(List<Work> staff) {
        this.staff = staff;
    }

    public void hireDoctor(Long id, Doctor d, LocalTime time1, LocalTime time2) {
        // doda work objekat u doktora i u apoteku
        if (d.getClass() == Pharmacist.class) {
            if (!d.getWorking().isEmpty()){
                System.out.println("apotekar je vec zaposlen");
            } else {
                Work work = new Work(id, d, this, time1, time2);
                d.getWorking().add(work);
                this.staff.add(work);
            }
        } else if (d.getClass() == Dermatologist.class) {
            // provara da li je dermatolog vec zaposlen tu? ili spolja da se uradi provera
            Work work = new Work(id, d, this, time1, time2);
            d.getWorking().add(work);
            this.staff.add(work);
        }
    }

    public List<MedicineOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MedicineOrder> orders) {
        this.orders = orders;
    }

    public List<MedicineInPharmacy> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineInPharmacy> medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", staff=" + staff +
                ", orders=" + orders +
                ", medicines=" + medicines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(name, pharmacy.name) && Objects.equals(address, pharmacy.address) && Objects.equals(description, pharmacy.description) && id.equals(pharmacy.id) && Objects.equals(staff, pharmacy.staff) && Objects.equals(orders, pharmacy.orders) && Objects.equals(medicines, pharmacy.medicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, description, id, staff, orders, medicines);
    }
}
