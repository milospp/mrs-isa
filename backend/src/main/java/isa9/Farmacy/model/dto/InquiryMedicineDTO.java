package isa9.Farmacy.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryMedicineDTO {
    private Long id;
    private LocalDateTime inquiryDate;
    private MedicineDTO medicine;
    private DoctorDTO doctor;
}
