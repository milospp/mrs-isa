package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class MedicineSearchDTO extends DoctorDTO {

    private String name = "";
    private String structure = "";
    private String manufacturer = "";
    private String shape = "";
    private String type = "";
    private String perscription = "ALL";


    private double minPoints = 0;
    private double maxPoints = 100;
    private double minRating = 0;
    private double maxRating = 5;
    private String sort = "NAME_ASC";

}
