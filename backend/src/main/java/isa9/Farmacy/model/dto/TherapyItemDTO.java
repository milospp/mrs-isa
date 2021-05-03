package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Medicine;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TherapyItemDTO {

    private MedInPharmaDTO medInPharma;

    private int days;
}
