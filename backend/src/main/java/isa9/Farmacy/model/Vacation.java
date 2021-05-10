package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vacation {
    private boolean accepted;
    private TypeOfRest type;
    private Date startDate;
    private Date endDate;
    private String whyNot;


    @Override
    public int hashCode() {
        return Objects.hash(accepted, type, startDate, endDate, whyNot);
    }
}
