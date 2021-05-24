package isa9.Farmacy.model;

import com.sun.istack.NotNull;
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
@EqualsAndHashCode(exclude = {"medicines","orders","staff","ratings","description","id"})
@ToString(exclude = {"staff", "medicines"})
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
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

    @Column (nullable = false, columnDefinition="Decimal(2,1) default '0.0'")
    @Builder.Default
    private double rating = 0.0;

    @Column
    @NotNull
    private double pricePerHour;

    @OneToMany(fetch = FetchType.EAGER)
    private List<InquiryMedicine> inquiryMedicines;


    public Pharmacy(String name, Address address, String description, Long id) {
        super();
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        this.staff = new HashSet<>();
        this.orders = new HashSet<>();
        this.medicines = new HashSet<>();
        this.inquiryMedicines = new ArrayList<>();
    }

    public Pharmacy(String name, Address address, String description, Long id, Set<Work> staff, Set<MedicineOrder> orders, Set<MedicineInPharmacy> medicines) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.id = id;
        this.staff = staff;
        this.orders = orders;
        this.medicines = medicines;
        this.inquiryMedicines = new ArrayList<>();
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
