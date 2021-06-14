package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    @EqualsAndHashCode.Include
    private LocalDateTime startTime;

    @Column
    @EqualsAndHashCode.Include
    private double price;
    @Column
    @EqualsAndHashCode.Include
    private int durationInMins;

    @Column
    @Enumerated
    @EqualsAndHashCode.Include
    private TypeOfReview type;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Column(name = "version", nullable = false, columnDefinition = "int default 0")
    @Version
    private Long version;
}
