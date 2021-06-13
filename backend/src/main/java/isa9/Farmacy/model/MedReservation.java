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

    @Column
    private Double loyaltyDiscount;

    @ManyToOne
    private Pharmacist whoDispenses;

    public MedReservation(Long dbId, String dbCode, Patient p, LocalDate dbResDate, LocalDate dbLastDate, MedReservationStatus dbStatus, MedicineInPharmacy mip, int dbQuantity, Pharmacist dispenser) {
        this.id = dbId;
        this.code = dbCode;
        this.patient = p;
        this.reservationDate = dbResDate;
        this.lastDate = dbLastDate;
        this.status = dbStatus;
        this.medicineInPharmacy = mip;
        this.quantity = dbQuantity;
        this.whoDispenses = dispenser;
        this.loyaltyDiscount = 0d;
    }
}
