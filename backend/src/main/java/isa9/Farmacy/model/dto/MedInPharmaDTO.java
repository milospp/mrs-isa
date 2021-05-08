package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.MedPrice;
import isa9.Farmacy.model.MedSpecification;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Pharmacy;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedInPharmaDTO {
    private Long id;
    private double currentPrice;
    private MedicineDTO medicine;
    private int inStock;
    private PharmacyDTO pharmacy;

}
