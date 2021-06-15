package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.TypeOfReview;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConsultingAppointmentReqDTO {
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    //@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime startTime;
    private int durationInMins;
    private Long pharmacistId;
    private Long pharmacyId;

}
