package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Dermatologist extends Doctor{



    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, Set<Work> working) {
        super(id, name, surname, email, password, address, phoneNumber);
        //this.pharmacies = new ArrayList<Pharmacy>();
        this.setWorking(working);
        this.setRole(UserRole.DERMATOLOGIST);
    }


}
