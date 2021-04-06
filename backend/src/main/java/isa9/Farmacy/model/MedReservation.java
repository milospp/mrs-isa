package isa9.Farmacy.model;

import javax.persistence.*;
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
    private Long id;

    @Column(unique = true)
    private String code;

    @ManyToOne
    private Patient patient;
    @Column
    private Date reservationDate;
    @Column
    private Date lastDate;
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
