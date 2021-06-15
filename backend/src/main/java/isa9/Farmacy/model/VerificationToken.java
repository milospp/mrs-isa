package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String token;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime expirationDate;

    @OneToOne(targetEntity = Patient.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "patient_id")
    private Patient patient;

    public VerificationToken(Patient patient){
        this.patient = patient;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.expirationDate = this.createdDate.plusDays(3);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.getExpirationDate());
    }
}
