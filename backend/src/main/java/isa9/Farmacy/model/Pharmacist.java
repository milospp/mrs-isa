package isa9.Farmacy.model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class Pharmacist extends Doctor{

    public Pharmacist() {
    }

    public Pharmacist(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, Work work) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.setWorking(new HashSet<>());
        this.getWorking().add(work);
    }

    public Pharmacist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.setWorking(new HashSet<>());
    }

    public Pharmacy getPharmacy() {
        return this.getWorking().iterator().next().getPharmacy();
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
        return "Pharmacist: " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }
}
