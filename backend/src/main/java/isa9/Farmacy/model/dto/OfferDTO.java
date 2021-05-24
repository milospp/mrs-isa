package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.OfferStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferDTO {
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String offerDescription;
    private SupplierDTO supplier;
    private Long order;             // da ne bude petlja
    private OfferStatus status;
    private Long id;
}
