package isa9.Farmacy.model;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
