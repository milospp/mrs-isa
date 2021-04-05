package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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


}
