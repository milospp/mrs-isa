package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Medicine {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @EqualsAndHashCode.Include
    private String code;
    @Column
    @EqualsAndHashCode.Include
    private String name;
    @Column
    @EqualsAndHashCode.Include
    private String structure;
    @Column
    @EqualsAndHashCode.Include
    private String manufacturer;
    @Column
    @EqualsAndHashCode.Include
    private String note;
    @Column
    private int points;
    @Column
    @EqualsAndHashCode.Include
    private String shape;
    @Column
    @EqualsAndHashCode.Include
    private String type;
    @Enumerated
    @EqualsAndHashCode.Include
    private DispencingMedicine perscription;
    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Medicine> replacementMedication;
    @Column (nullable = false, columnDefinition="Decimal(2,1) default '0.0'")
    private double rating = 0.0;

    // TODO Medicine Stock


    public Medicine(Long id, String code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, Set<Medicine> replacementMedication) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.structure = structure;
        this.manufacturer = manufacturer;
        this.note = note;
        this.points = points;
        this.shape = shape;
        this.type = type;
        this.perscription = perscription;
        this.replacementMedication = replacementMedication;
    }
}
