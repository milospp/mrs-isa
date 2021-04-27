package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.model.TypeOfReview;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedReservationFormDTO {

    private Long medicineId;
    private Long pharmacyId;
    private Long patientId;
    private int quantity;

    private LocalDate expirityDate;

}
