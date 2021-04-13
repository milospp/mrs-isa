package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    private Patient patient;
    @OneToOne
    @EqualsAndHashCode.Include
    private Appointment appointment;
    @Enumerated
    @EqualsAndHashCode.Include
    private ExaminationStatus status; // held, not held or canceled
    @Column
    @EqualsAndHashCode.Include
    private String examinationInfo;
    @Column
    @EqualsAndHashCode.Include
    private String diagnose;

//    @ElementCollection
//    @CollectionTable(name = "therapy",
//            joinColumns = {@JoinColumn(name = "therapy_id", referencedColumnName = "id")})
//    @MapKeyColumn(name = "therapy_medicine")
//    @Column
//    private Map<Medicine, Integer> therapy; // maybe should be the code of the medicine

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TherapyItem> therapy;

}
