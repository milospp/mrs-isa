package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.TypeOfReview;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AppointmentDTO {
    private Long id;
    @JsonFormat(pattern="yyyy-M-d HH:mm:ss")
    private LocalDateTime startTime;
    private double price;
    private int durationInMins;
    private TypeOfReview type;

    private DoctorDTO doctor;
    private PharmacyDTO pharmacy;
    private ExaminationDTO examination;

    
}
