package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class Appointment {
    @Id
    private Long id;

    @Column
    private LocalDateTime startTime;

    @Column
    private double price;
    @Column
    private int durationInMins;

    @Column
    @Enumerated
    private TypeOfReview type;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

}
