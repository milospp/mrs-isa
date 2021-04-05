package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.DispencingMedicine;
import isa9.Farmacy.model.Medicine;

import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedicineDTO {
    private String code;
    private String name;
    private String structure;
    private String manufacturer;
    private String note;
    private int points;
    private String shape;
    private String type;
    private DispencingMedicine perscription;
    private List<String> replacementMedicationIds;


}



