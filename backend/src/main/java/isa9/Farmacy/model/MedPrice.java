package isa9.Farmacy.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private double price;
    @ManyToOne
    private MedicineInPharmacy medicineInPharmacy;




}
