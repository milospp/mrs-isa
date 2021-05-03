package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Pharmacy;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class PharmacySearchDTO extends DoctorDTO {

    private String name = "";
    private String addressString = "";
    private double minRating = 0;
    private double maxRating = 5;
    private String sort = "NAME_ASC";

}
