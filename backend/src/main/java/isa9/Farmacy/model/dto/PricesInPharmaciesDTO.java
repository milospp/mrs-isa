package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class PricesInPharmaciesDTO {
    private double price;
    private String pharmacyName;
}
