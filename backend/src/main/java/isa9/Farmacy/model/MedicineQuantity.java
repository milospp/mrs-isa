package isa9.Farmacy.model;
// klasa za lekove koji su poruceni u narudzbenici

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
public class MedicineQuantity {
    @Id
    private Long id;
    @ManyToOne
    private Medicine medicine;
    @Column
    private int quantity;



    public MedicineQuantity(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }


}
