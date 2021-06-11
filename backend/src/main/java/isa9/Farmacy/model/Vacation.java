package isa9.Farmacy.model;

import java.time.LocalDate;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Vacation {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @EqualsAndHashCode.Include
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column
    @Enumerated
    private VacationRequestStatus status;

    @Column
    @Enumerated
    @EqualsAndHashCode.Include
    private TypeOfRest type;

    @Column
    @EqualsAndHashCode.Include
    private LocalDate startDate;

    @Column
    @EqualsAndHashCode.Include
    private LocalDate endDate;

    @Column
    private String reason;

    @Column
    private String whyNot;
}
