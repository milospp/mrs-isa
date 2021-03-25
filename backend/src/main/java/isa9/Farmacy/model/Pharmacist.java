package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Pharmacist extends Doctor{

    private Pharmacy pharmacy;

    public Pharmacist() {
    }

    public Pharmacist(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, Pharmacy pharmacy) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.pharmacy = pharmacy;
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

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
