package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "medicine_specifications")
@Entity

public class MedSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="side_effects")
    private String sideEffects;

    @Column(name="daily_intake")
    private String dailyIntake;

    @Column
    private String structure;
}
