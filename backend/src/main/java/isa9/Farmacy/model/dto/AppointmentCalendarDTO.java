package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.TypeOfReview;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AppointmentCalendarDTO {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;
    private int durationInMins;
    private String patientName;
    private String patientSurname;
    private String pharmacyName;
    private String typeForClass;
    private Double price;
}
