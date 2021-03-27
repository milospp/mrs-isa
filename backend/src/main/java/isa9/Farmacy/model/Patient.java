package isa9.Farmacy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Patient extends User {

    private int points;
    private Set<Penality> penalties;
    private Set<Pharmacy> subscriptions;
    private Set<Examination> myExaminations;
    private Set<Medicine> allergies;

    public Patient() {
    }

    public Patient(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.PATIENT);
        this.points = 0;
        this.penalties = new HashSet<>();
        this.subscriptions = new HashSet<>();
        this.myExaminations = new HashSet<>();
        this.allergies = new HashSet<>();

    }

    @Deprecated
    public Patient(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, int points,
                   Set<Penality> penalties, List<Examination> myExaminations) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.PATIENT);
        this.points = points;
        this.penalties = penalties;
        this.subscriptions = new HashSet<>();
        this.myExaminations = new HashSet<>();
        this.allergies = new HashSet<>();

    }

    public Patient(Long id, String name, String surname, String email,
                   String password, Address address, String phoneNumber, int points,
                   Set<Penality> penalties, Set<Pharmacy> subscriptions , Set<Examination> myExaminations,
                   Set<Medicine> allergies) {
        super(id, name, surname, email, password, address, phoneNumber, UserRole.PATIENT);
        this.points = points;
        this.penalties = penalties;
        this.subscriptions = subscriptions;
        this.myExaminations = myExaminations;
        this.allergies = allergies;
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
        return "Patient{" +
                "points=" + points +
                ", penalties=" + penalties +
                ", subscriptions=" + subscriptions +
                ", myExaminations=" + myExaminations +
                ", allergies=" + allergies +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Penality> getPenalties() {
        return penalties;
    }

    public void setPenalties(Set<Penality> penalties) {
        this.penalties = penalties;
    }

    public Set<Pharmacy> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Pharmacy> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Examination> getMyExaminations() {
        return myExaminations;
    }

    public void setMyExaminations(Set<Examination> myExaminations) {
        this.myExaminations = myExaminations;
    }

    public Set<Medicine> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Medicine> allergies) {
        this.allergies = allergies;
    }
}
