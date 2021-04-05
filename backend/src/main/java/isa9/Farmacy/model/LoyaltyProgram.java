package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private Long id;

    @Column
    private String name;

    @Column
    private int requiredPoints;

    @Column
    private double discount;


}
