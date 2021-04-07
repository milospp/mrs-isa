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
@EqualsAndHashCode(exclude = {"currentPrice", "pharmacy"})
public class MedicineInPharmacy {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private MedPrice currentPrice;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int inStock;
    @ManyToOne
    private Pharmacy pharmacy; // mozda ne treba


}
