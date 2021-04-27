package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.print.Doc;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "doctor_id"})
)
@Entity
public class RatingDoctor extends Rating {
    @ManyToOne
    private Doctor doctor;

    public RatingDoctor(Long id, User user, int rating, Doctor doctor) {
        super(id, user, rating);
        this.doctor = doctor;
    }
}
