package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"medicineAtSupplier"})
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@EqualsAndHashCode
public class SupplierMedPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private double price;
    @ManyToOne
    private MedicineAtSupplier medicineAtSupplier;




}
