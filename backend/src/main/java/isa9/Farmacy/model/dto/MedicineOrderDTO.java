package isa9.Farmacy.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineOrderDTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private OfferDTO chosenOffer;
    private List<OfferDTO> allOffer;
    private PharmacyDTO pharmacy;
    private List<MedicineQuantityDTO> allMedicines;
    private PharmacyAdminDTO author;
}
