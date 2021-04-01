package isa9.Farmacy.model;

import java.util.List;

public abstract class Doctor extends User{

    private List<Work> working;

    public Doctor() {
    }

    public Doctor(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);

    }

    public List<Work> getWorking() {
        return working;
    }

    public void setWorking(List<Work> working) {
        this.working = working;
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
        return "Doctor: " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }

}
