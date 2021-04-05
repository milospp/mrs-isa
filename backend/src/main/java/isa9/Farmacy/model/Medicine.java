package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Medicine {
    @Id
    private String code;
    @Column
    private String name;
    @Column
    private String structure;
    @Column
    private String manufacturer;
    @Column
    private String note;
    @Column
    private int points;
    @Column
    private String shape;
    @Column
    private String type;
    @Enumerated
    private DispencingMedicine perscription;
    @ManyToMany
    private Set<Medicine> replacementMedication;

    // TODO Medicine Stock





}
