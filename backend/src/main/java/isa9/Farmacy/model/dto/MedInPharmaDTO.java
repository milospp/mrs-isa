package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedInPharmaDTO {
    private Long id;
    private Double currentPrice;
    private PriceType priceType;    // tip
    private Double oldPrice;           // za akciju i promociju
    private LocalDateTime endDate;  // kraj akcije ili promocije
    private MedicineDTO medicine;
    private Integer inStock;
    private PharmacyDTO pharmacy;

}
