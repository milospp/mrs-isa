package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Rating {
    @Id
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Pharmacy pharmacy;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int rating;



    public Rating(User user, Pharmacy pharmacy, Medicine medicine, int rating) {
        super();
        this.user = user;
        this.pharmacy = pharmacy;
        this.medicine = medicine;
        this.rating = rating;
    }




}
