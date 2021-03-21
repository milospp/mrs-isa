package isa9.Farmacy.model;

import java.util.List;

public class Dermatologist extends Doctor{

    private List<Pharmacy> pharmacies;

    public Dermatologist() {
    }

    public Dermatologist(Long id, String username, String name, String surname, String email, String password) {
        super(id, username, name, surname, email, password);
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
        return "Dermatologist: " + getUsername() + " " + getName() + " " + getSurname() + " " + getEmail() + " " + getPassword();
    }

}
