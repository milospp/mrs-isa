package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class PharmacyAdmin extends User{

    @ManyToOne
    private Pharmacy pharmacy;

   
}
