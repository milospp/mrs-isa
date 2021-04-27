package isa9.Farmacy.model;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.*;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder

@Entity
public class Pharmacist extends Doctor{

    public Pharmacist(Long id, String name, String surname, String email,
                      String password, Address address, String phoneNumber,
                      UserRole role, Timestamp t) {
        super(id, name, surname, email, password, address, phoneNumber, role, new HashSet<>(), t);
    }


}
