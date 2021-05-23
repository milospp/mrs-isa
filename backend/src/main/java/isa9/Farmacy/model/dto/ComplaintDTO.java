package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ComplaintDTO {
    private long id, author, pharmacy, doctor;

    private String description;

    private String response;
}
