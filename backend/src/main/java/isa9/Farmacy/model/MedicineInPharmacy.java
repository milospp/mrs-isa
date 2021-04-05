package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@EqualsAndHashCode
public class MedicineInPharmacy {
    @Id
    private String id;
    @OneToOne
    private MedPrice currentPrice;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int inStock;
    @ManyToOne
    private Pharmacy pharmacy; // mozda ne treba


}
