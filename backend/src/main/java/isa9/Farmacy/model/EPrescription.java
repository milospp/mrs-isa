package isa9.Farmacy.model;

import javax.persistence.*;
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

@Entity
public class EPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;
    @ManyToOne
    private Patient patient;
    @Column
    private Date issueDate;

}
