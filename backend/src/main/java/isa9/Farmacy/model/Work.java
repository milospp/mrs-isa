package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalTime;
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
    @Column
    private double salaryPerHour;


    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
