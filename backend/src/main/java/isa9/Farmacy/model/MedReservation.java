package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class MedReservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Column
    private LocalDate reservationDate;
    @Column
    private LocalDate lastDate;
    @Column
    private MedReservationStatus status;

    @ManyToOne
    private MedicineInPharmacy medicineInPharmacy;
    @Column
    private int quantity;

    @ManyToOne
    private Pharmacist whoDispenses;

}
