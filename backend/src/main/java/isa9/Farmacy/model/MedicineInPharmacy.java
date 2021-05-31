package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
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
public class MedicineInPharmacy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private MedPrice currentPrice;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int inStock;
    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy; // mozda ne treba

    @Column(name = "version", nullable = false, columnDefinition = "int default 0")
    @Version
    private Long version;

}
