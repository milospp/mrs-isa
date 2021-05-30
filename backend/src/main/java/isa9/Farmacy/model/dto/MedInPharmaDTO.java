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
    private double currentPrice;
    private PriceType priceType;    // tip
    private double oldPrice;           // za akciju i promociju
    private LocalDateTime startDate;  // pocetak akcije ili promocije
    private LocalDateTime endDate;  // kraj akcije ili promocije
    private MedicineDTO medicine;
    private int inStock;
    private PharmacyDTO pharmacy;

}
