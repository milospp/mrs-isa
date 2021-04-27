package isa9.Farmacy.model;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;
    @Column
    private String medName;
    @Column
    private int quantity;
    @ManyToOne
    private EPrescription eRecipe;
    

}
