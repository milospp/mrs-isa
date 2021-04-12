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

    // TODO Medicine Stock





}
