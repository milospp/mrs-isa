package isa9.Farmacy.model;

import javax.persistence.*;
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

public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Patient author;
    @ManyToOne
    private SysAdmin respondent;
    @OneToOne
    private Pharmacy pharmacy;
    @OneToOne
    private Doctor doctor;
    @Column
    private String description;
    @Column
    private String response;

    @Column(name = "version", nullable = false, columnDefinition = "int default 0")
    @Version
    private Long version;

}
