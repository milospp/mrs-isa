package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalTime;
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
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Pharmacy pharmacy;
    @Column
    private LocalTime startHour;
    @Column
    private LocalTime endHour;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return id.equals(work.id) && Objects.equals(doctor, work.doctor) && Objects.equals(pharmacy, work.pharmacy) && Objects.equals(startHour, work.startHour) && Objects.equals(endHour, work.endHour);
    }
}
