package isa9.Farmacy.support;

import isa9.Farmacy.model.dto.PatientDTO;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientsPagesDTO {
    private long count;
    private List<PatientDTO> patients;
}
