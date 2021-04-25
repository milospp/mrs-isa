package isa9.Farmacy.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DermaDTOHelp {
    private DermatologistDTO registerData;
    private String startHour;
    private String endHour ;
}