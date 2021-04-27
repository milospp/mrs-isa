package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {

    private Long id;
    private Long user;
    private int rating;


}
