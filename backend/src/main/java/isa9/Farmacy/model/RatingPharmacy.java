package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class RatingPharmacy extends Rating {
    @ManyToOne
    private Pharmacy pharmacy;

    public RatingPharmacy(Long id, User user, int rating, Pharmacy pharmacy) {
        super(id, user, rating);
        this.pharmacy = pharmacy;
    }
}
