package isa9.Farmacy.model.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PenalityDTO {
    private Long id;
    private String reason;
    private LocalDate date;


}
