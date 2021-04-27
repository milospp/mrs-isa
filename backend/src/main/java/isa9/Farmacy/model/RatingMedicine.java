package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class RatingMedicine extends Rating {
    @ManyToOne
    private Medicine medicine;

    public RatingMedicine(Long id, User user, int rating, Medicine medicine) {
        super(id, user, rating);
        this.medicine = medicine;
    }
}
