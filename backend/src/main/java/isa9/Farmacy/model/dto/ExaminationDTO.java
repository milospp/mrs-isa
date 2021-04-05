package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;

import java.util.Map;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExaminationDTO {

    private Long id;
    private PatientDTO patient;
    private Long appointment;
    private ExaminationStatus status; // held, not held or canceled
    private String examinationInfo;
    private String diagnose;
    private Map<String, Integer> therapy; // maybe should be the code of the medicine
    private TypeOfReview type;





}
