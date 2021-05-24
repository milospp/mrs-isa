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
public class PricelistDTO {
    private LocalDateTime lastEditing;
    private List<MedInPharmaDTO> medicines;

}
