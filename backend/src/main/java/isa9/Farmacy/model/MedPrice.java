package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class MedPrice {
    // za svaku promenu cene, pravi se nova klasa cene
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;  // za akcije i promocije
    @Column
    private PriceType priceType;    // da li je obicna, akcija ili promocija
    @Column
    private double oldPrice;           // cuvamo staru vrednost kako bismo je vratili
    @Column
    private double price;
    @ManyToOne
    private MedicineInPharmacy medicineInPharmacy;




}
