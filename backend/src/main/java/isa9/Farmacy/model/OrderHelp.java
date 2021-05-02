package isa9.Farmacy.model;

import isa9.Farmacy.model.dto.MedicineQuantityDTO;
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
public class OrderHelp {
    private List<MedicineQuantityDTO> medicines;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
