package isa9.Farmacy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_admin")
public class SysAdmin extends User{

    public SysAdmin() {
    }

    public SysAdmin(Long id, String name, String surname, String email,
                      String password, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.SYS_ADMIN);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "SysAdmin: " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }
}
