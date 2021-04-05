package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Map;
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
public class Examination {
    @Id
    private Long id;
    @ManyToOne
    private Patient patient;
    @OneToOne
    private Appointment appointment;
    @Enumerated
    private ExaminationStatus status; // held, not held or canceled
    @Column
    private String examinationInfo;
    @Column
    private String diagnose;

    @ElementCollection
    @CollectionTable(name = "therapy",
            joinColumns = {@JoinColumn(name = "therapy_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "therapy_medicine")
    @Column
    private Map<Medicine, Integer> therapy; // maybe should be the code of the medicine




}
