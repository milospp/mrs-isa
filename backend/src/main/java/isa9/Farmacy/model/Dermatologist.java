package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder

@Entity
public class Dermatologist extends Doctor{

    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.DERMATOLOGIST);
    }

    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, Set<Work> working) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.DERMATOLOGIST, working);
    }


}
