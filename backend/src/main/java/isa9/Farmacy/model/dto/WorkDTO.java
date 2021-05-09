package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;

import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkDTO {
    private Long id;
    private Long doctorId;
    private Long pharmacyId;
    private PharmacyDTO pharmacyDTO;
    private DoctorDTO doctorDTO;
    private String pharmacyName;
    private Address pharmacyAddr;
    private String pharmacyDesc;
    private LocalTime startHour;
    private LocalTime endHour;
    private double salaryPerHour;

}
