package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import isa9.Farmacy.model.dto.DoctorDTO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Doctor extends User{

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Work> working;



    public Doctor(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, UserRole role) {
        super(id, name, surname, email, password, address, phoneNumber, role, true);
        working = new HashSet<>();
    }

    public Doctor(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, UserRole role, Set<Work> working) {
        super(id, name, surname, email, password, address, phoneNumber, role, true);
        this.working = working;
    }

}
