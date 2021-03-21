package isa9.Farmacy.model;

public abstract class Doctor extends User{

    public Doctor() {
    }

    public Doctor(Long id, String username, String name, String surname, String email, String password) {
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
        return "Doctor: " + getUsername() + " " + getName() + " " + getSurname() + " " + getEmail() + " " + getPassword();
    }

}
