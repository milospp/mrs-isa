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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerHelp that = (WorkerHelp) o;
        return Objects.equals(registerData, that.registerData) && Objects.equals(startHour, that.startHour) && Objects.equals(endHour, that.endHour);
    }
}
