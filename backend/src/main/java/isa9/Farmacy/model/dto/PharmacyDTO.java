package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PharmacyDTO {

    private Long id;
    private String name;
    private String description;
    private Address address;
    private double rating;


}
