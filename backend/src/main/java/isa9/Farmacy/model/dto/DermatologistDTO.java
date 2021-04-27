package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class DermatologistDTO extends DoctorDTO {

    private List<Pharmacy> pharmacies;
    private double rating;


}
