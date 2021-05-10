package isa9.Farmacy.model;

import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkerHelp {
    private Pharmacist registerData;
    private String startHour;
    private String endHour ;

    @Override
    public int hashCode() {
        return Objects.hash(registerData, startHour, endHour);
    }

}
