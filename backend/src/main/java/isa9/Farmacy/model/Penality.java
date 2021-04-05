package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Penality {
    @Id
    private Long id;
    @Column
    private String reason;
    @Column
    private LocalDate date;


    public Penality(String reason, LocalDate date) {
        super();
        this.reason = reason;
        this.date = date;
    }




}
