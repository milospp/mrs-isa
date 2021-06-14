package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalTime;
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
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Pharmacy pharmacy;
    @Column
    private LocalTime startHour;
    @Column
    private LocalTime endHour;

}
