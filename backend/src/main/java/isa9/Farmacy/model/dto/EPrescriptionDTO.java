package isa9.Farmacy.model.dto;

import lombok.*;
import org.hibernate.boot.jaxb.internal.stax.LocalXmlResourceResolver;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EPrescriptionDTO {
    private Long id;
    private String code;
    private PatientDTO patient;
    private LocalDate issueDate;
    private Map<String, Integer> medicines;
    private PharmacyDTO pharmacy;
}
