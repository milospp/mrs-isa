package isa9.Farmacy.model.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class OfferSearchDTO {
    private String description = "", status = "INDEFINITELY", sort = "DESC_ASC";
    private double minPrice = 0, maxPrice = Integer.MAX_VALUE;
    private LocalDateTime startDate, endDate;
}
