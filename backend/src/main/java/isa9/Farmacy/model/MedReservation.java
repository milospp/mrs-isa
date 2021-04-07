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
    private Patient patient;
    @Column
    private LocalDate reservationDate;
    @Column
    private LocalDate lastDate;
    @Column
    private boolean taken;
    @Column
    private boolean canceled;

    @ManyToOne
    private Medicine medicine;
    @Column
    private int quantity;

    @ManyToOne
    private Pharmacist issued;
    @ManyToOne
    private Pharmacy pharmacy;


}
