package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dermatologist extends Doctor{

    public Dermatologist() {
    }

    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);
        //this.pharmacies = new ArrayList<Pharmacy>();
        this.setWorking(new HashSet<>());
        this.setRole(UserRole.DERMATOLOGIST);
    }

    public Dermatologist(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, Set<Work> working) {
        super(id, name, surname, email, password, address, phoneNumber);
        //this.pharmacies = new ArrayList<Pharmacy>();
        this.setWorking(working);
        this.setRole(UserRole.DERMATOLOGIST);
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
        List<Pharmacy> pharmacies = new ArrayList<>();
        for (Work work : getWorking()){
            pharmacies.add(work.getPharmacy());
        }
        return pharmacies;
    }

//    public void setPharmacies(List<Pharmacy> pharmacies) {
//        this.pharmacies = pharmacies;
//    }
}
