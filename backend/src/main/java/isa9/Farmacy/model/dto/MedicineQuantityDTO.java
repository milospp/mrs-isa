package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;

import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedicineQuantityDTO {
    private MedicineDTO medicine;
    private int quantity;

}
