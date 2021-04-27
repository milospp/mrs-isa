package isa9.Farmacy.model;

import java.sql.Timestamp;
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

    public Dermatologist(long l, String name, String surname, String email, String password,
                         Address address, String phoneNumber, UserRole role, Timestamp t) {
        super(l, name, surname, email, password, address, phoneNumber, role, new HashSet<>(), t);
    }
}
