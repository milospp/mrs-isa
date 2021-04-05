package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Patient extends User {
    @Column
    private int points;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Penality> penalties;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Pharmacy> subscriptions;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Examination> myExaminations;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Medicine> allergies;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<MedReservation> reservations;

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
        this.reservations = new HashSet<>();

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
        this.reservations = new HashSet<>();

    }

    @Deprecated
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
        this.reservations = new HashSet<>();
    }

    public Patient(Long id, String name, String surname, String email, String password, Address address, String phoneNumber, UserRole userRole, int points, Set<Penality> penalties, Set<Pharmacy> subscriptions, Set<Examination> myExaminations, Set<Medicine> allergies, Set<MedReservation> reservations) {
        super(id, name, surname, email, password, address, phoneNumber, userRole);
        this.points = points;
        this.penalties = penalties;
        this.subscriptions = subscriptions;
        this.myExaminations = myExaminations;
        this.allergies = allergies;
        this.reservations = reservations;
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

    public Set<MedReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<MedReservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "points=" + points +
                ", penalties=" + penalties +
                ", subscriptions=" + subscriptions +
                ", myExaminations=" + myExaminations +
                ", allergies=" + allergies +
                ", reservations=" + reservations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return points == patient.points && Objects.equals(penalties, patient.penalties) && Objects.equals(subscriptions, patient.subscriptions) && Objects.equals(myExaminations, patient.myExaminations) && Objects.equals(allergies, patient.allergies) && Objects.equals(reservations, patient.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points, penalties, subscriptions, myExaminations, allergies, reservations);
    }
}
