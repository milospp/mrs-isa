package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class MedicineAtSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private SupplierMedPrice supplierPrice;
    @OneToOne(cascade = CascadeType.ALL)
    private MedicineQuantity quantity;
    @ManyToOne
    private Supplier supplier;
}
