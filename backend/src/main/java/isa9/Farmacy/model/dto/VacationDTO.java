package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.TypeOfRest;
import isa9.Farmacy.model.VacationRequestStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationDTO {

    private Long id;
    private Long doctorId;
    private Long pharmacyId;
    private Long pharmacyAdminId;
    private String reason;
    private String whyNot;
    private LocalDate startDate;
    private LocalDate endDate;
    private TypeOfRest type;
    private VacationRequestStatus status;

}
