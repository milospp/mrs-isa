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

//@Entity
//@NamedNativeQuery(
//        name = "find_p_last_app_dtos",
//        query =
//                "select pap.* from " +
//                        "(select distinct p.*, max(a.start_time) as last " +
//                        "from users as p, appointment as a, examination as e " +
//                        "where p.id = e.patient_id and e.appointment_id = a.id " +
//                        "and a.doctor_id = :doctorId and e.status != 3 " +
//                        "and LOWER(p.name) like CONCAT('%',LOWER(:name),'%') " +
//                        "and LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%') "+
//                        "group by p.id) as pap",
//        resultSetMapping = "p_last_app_dtos"
//)
//@SqlResultSetMapping(
//        name = "p_last_app_dtos",
//        classes = @ConstructorResult(
//                targetClass = PatientLastAppointmentDTO.class,
//                columns = {
//                        @ColumnResult(name = "id", type = Long.class),
//                        @ColumnResult(name = "name", type = String.class),
//                        @ColumnResult(name = "surname", type = String.class),
//                        @ColumnResult(name = "address", type = Address.class),
//                        @ColumnResult(name = "phone_number", type = String.class),
//                        @ColumnResult(name = "role", type = RolesDTO.class),
//                        @ColumnResult(name = "email", type = String.class),
//                        @ColumnResult(name = "last", type = LocalDateTime.class)
//                }
//        )
//)
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
