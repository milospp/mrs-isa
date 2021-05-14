package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.model.Patient;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PatientLastAppointmentDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Address address;
    @JsonFormat(pattern="yyyy-M-d HH:mm")
    private LocalDateTime last;
    private Long aid;
    private ExaminationStatus status;

    public PatientLastAppointmentDTO(Patient patient, LocalDateTime last) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.surname = patient.getSurname();
        this.email = patient.getEmail();
        this.address = patient.getAddress();
        this.phoneNumber = patient.getPhoneNumber();
        this.last = last;
    }
}
