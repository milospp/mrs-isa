package isa9.Farmacy.model;

import java.util.ArrayList;

public class Supplier extends User{

    private ArrayList<Offer> myOffers;

    public Supplier() {
    }

    public Supplier(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.SUPPLIER);
        myOffers = new ArrayList<Offer>();
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
        return "Supplier: " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }

}
