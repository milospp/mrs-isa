package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Doctor extends User{

    public Doctor() {
    }

    public Doctor(Long id, String name, String surname, String email,
                   String password, String username, Address address, String phoneNumber) {
        super(id, username, name, surname, email, password, address, phoneNumber);

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
        return "Doctor: " + getUsername() + " " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }

}
