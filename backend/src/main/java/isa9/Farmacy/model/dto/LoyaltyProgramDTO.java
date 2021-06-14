package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoyaltyProgramDTO {
    private Long id;
    private String name;
    private int requiredPoints;
    private double discount;
}
