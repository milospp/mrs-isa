package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@EqualsAndHashCode
public class InquiryMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime inquiryDate;
    @Column
    private Long pharmacyId;
    @ManyToOne
    private Medicine medicine;
    @ManyToOne
    private Doctor doctor;

}
