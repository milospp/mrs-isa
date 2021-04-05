package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;

@Entity
@EqualsAndHashCode(exclude = "medicines")
@ToString
public class Pharmacy {
    @Id
    private Long id;

    @Column
    private String name;
    @ManyToOne
    private Address address;
    @Column
    private String description;
    @ManyToMany
    private Set<Work> staff;

    @OneToMany
    private Set<MedicineOrder> orders;

    @OneToMany
    private Set<MedicineInPharmacy> medicines;

    @OneToMany
    private Set<Rating> ratings;

    public Pharmacy() {
        super();
        this.orders = new HashSet<>();
        this.staff = new HashSet<>();
        this.medicines = new HashSet<>();
    }

    public Pharmacy(String name, Address address, String description, Long id) {
        super();
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        this.staff = new HashSet<>();
        this.orders = new HashSet<>();
        this.medicines = new HashSet<>();
    }

    public Pharmacy(String name, Address address, String description, Long id, Set<Work> staff, Set<MedicineOrder> orders, Set<MedicineInPharmacy> medicines) {
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

    public Set<Work> getStaff() {
        return staff;
    }

    public void setStaff(Set<Work> staff) {
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

    public Set<MedicineOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<MedicineOrder> orders) {
        this.orders = orders;
    }

    public Set<MedicineInPharmacy> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<MedicineInPharmacy> medicines) {
        this.medicines = medicines;
    }




}
