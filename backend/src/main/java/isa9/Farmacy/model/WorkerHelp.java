package isa9.Farmacy.model;

import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkerHelp {
    @EqualsAndHashCode.Include
    private Pharmacist registerData;
    @EqualsAndHashCode.Include
    private String startHour;
    @EqualsAndHashCode.Include
    private String endHour ;

}
