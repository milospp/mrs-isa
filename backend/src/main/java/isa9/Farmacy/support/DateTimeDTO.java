package isa9.Farmacy.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateTimeDTO {
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime dateTime;
    int durationInMins;
    Long doctorId;
    Long patientId;
    Long pharmacyId;
    double price;
}
