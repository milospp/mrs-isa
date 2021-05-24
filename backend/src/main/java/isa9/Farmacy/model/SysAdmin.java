package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder

@Entity
@Table(name = "sys_admin")
public class SysAdmin extends User{


    public SysAdmin(long id, String name, String surname, String email, String password,
                    Address address, String phoneNumber, UserRole role, Timestamp timestamp) {
        super(id, name, surname, email, password, address, phoneNumber, role, true, timestamp);
    }
}
