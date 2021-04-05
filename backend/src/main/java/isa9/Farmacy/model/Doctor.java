package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
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
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Doctor extends User{

    @ManyToMany
    private Set<Work> working;



    public Doctor(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);

    }



}
