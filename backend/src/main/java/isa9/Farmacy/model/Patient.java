package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {

    private int points;
    private int penalties;
    private List<Examination> myExaminations;

    public Patient() {
    }

    public Patient(Long id, String name, String surname, String email,
                   String password, String username, Address address, String phoneNumber) {
        super(id, username, name, surname, email, password, address, phoneNumber);
        this.points = 0;
        this.penalties = 0;
        this.myExaminations = new ArrayList<>();

    }

    public Patient(Long id, String username, String name, String surname, String email,
                   String password, Address address, String phoneNumber, int points,
                   int penalties, List<Examination> myExaminations) {
        super(id, username, name, surname, email, password, address, phoneNumber);
        this.points = points;
        this.penalties = penalties;
        this.myExaminations = myExaminations;
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
        return "Patient: " + getUsername() + " " + getName() + " " + getSurname() + " " + getEmail()
                + " " + getPassword() + " " + getPhoneNumber() + " " + getAddress();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public List<Examination> getMyExaminations() {
        return myExaminations;
    }

    public void setMyExaminations(List<Examination> myExaminations) {
        this.myExaminations = myExaminations;
    }
}
