package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Dermatologist extends Doctor{

    private List<Pharmacy> pharmacies;

    public Dermatologist() {
    }

    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.pharmacies = new ArrayList<Pharmacy>();
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
        return "Dermatologist: " + getName() + " " + getSurname() + " " + getEmail() + " " + getPassword();
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }
}
