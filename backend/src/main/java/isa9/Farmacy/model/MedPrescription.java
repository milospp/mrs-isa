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
public class MedPrescription {

    @Column
    @Id
    private String code;
    @Column
    private String medName;
    @Column
    private int quantity;
    @ManyToOne
    private EPrescription eRecipe;
    

}
