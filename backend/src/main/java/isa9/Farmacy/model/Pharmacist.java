package isa9.Farmacy.model;

import java.util.ArrayList;

public class Pharmacist extends Doctor{

    public Pharmacist() {
    }

    public Pharmacist(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, Work work) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.setWorking(new ArrayList<>());
        this.getWorking().add(work);
    }

    public Pharmacist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.setWorking(new ArrayList<>());
    }

    public Pharmacy getPharmacy() {
        return this.getWorking().get(0).getPharmacy();
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
