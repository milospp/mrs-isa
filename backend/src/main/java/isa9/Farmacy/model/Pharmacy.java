package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Work> staff;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<MedicineOrder> orders;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<MedicineInPharmacy> medicines;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Rating> ratings;



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






}
