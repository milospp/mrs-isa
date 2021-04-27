package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class LoyaltyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int requiredPoints;

    @Column
    private double discount;


}
