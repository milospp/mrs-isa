package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedAtSupplierDTO {
    private Long id;
    private double currentPrice;
    private MedicineDTO medicine;
    private int inStock;
    private SupplierDTO supplier;
}
