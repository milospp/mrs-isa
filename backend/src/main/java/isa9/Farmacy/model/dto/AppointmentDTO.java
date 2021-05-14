package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.ExaminationDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.TypeOfReview;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;
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
