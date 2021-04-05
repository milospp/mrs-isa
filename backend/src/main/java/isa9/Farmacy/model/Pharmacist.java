package isa9.Farmacy.model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Pharmacist extends Doctor{



    public Pharmacist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.setWorking(new HashSet<>());
    }


}
