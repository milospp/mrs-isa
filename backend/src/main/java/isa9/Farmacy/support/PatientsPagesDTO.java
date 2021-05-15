package isa9.Farmacy.support;

import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.PatientLastAppointmentDTO;
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
public class PatientsPagesDTO {
    private long count;
    private List<PatientLastAppointmentDTO> patients;
}
