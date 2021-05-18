package isa9.Farmacy.model;
// klasa za lekove koji su poruceni u narudzbenici

import javax.persistence.*;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class MedicineQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
