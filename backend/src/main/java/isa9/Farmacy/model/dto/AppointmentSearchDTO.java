package isa9.Farmacy.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentSearchDTO extends DoctorDTO {

    private String doctorName = "";
    private String pharmacyName = "";
    private String status = "ALL";
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double minDuration = 0;
    private double maxDuration = 99999;
    private double minPrice = 0;
    private double maxPrice = 99999;
    private String sort = "DATE_DES";

}
