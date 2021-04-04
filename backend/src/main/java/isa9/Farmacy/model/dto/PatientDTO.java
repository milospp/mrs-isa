package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientDTO extends UserDTO{
    private int points;
    private List<Long> penalties;
    private List<Long> subscriptions;
    private List<Long> myExaminations;
    private List<String> allergies;
    private List<String> reservations;

    public PatientDTO() {}

    public PatientDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole role, String email, int points) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.points = points;
        this.penalties = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.myExaminations = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public PatientDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole role, String email, int points, List<Long> penalties, List<Long> subscriptions, List<Long> myExaminations, List<String> allergies, List<String> reservations) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.points = points;
        this.penalties = penalties;
        this.subscriptions = subscriptions;
        this.myExaminations = myExaminations;
        this.allergies = allergies;
        this.reservations = reservations;
    }

    @Deprecated
    public PatientDTO(Long id, String name, String surname, String address, String phoneNumber) {
        super(id, name, surname, new Address(), phoneNumber, UserRole.PATIENT);
        this.penalties = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.myExaminations = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    @Deprecated
    public PatientDTO(Long id, String name, String surname, Address address, String phoneNumber) {
        super(id, name, surname, address, phoneNumber, UserRole.PATIENT);
        this.points = 0;
        this.penalties = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.myExaminations = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }


    @Deprecated
    public PatientDTO(Long id, String name, String surname, Address address, String phoneNumber, int points) {
        super(id, name, surname, address, phoneNumber, UserRole.PATIENT);
        this.points = points;
        this.penalties = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.myExaminations = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    @Deprecated
    public PatientDTO(Long id, String name, String surname, Address address, String phoneNumber, int points, List<Long> penalties, List<Long> subscriptions, List<Long> myExaminations, List<String> allergies) {
        super(id, name, surname, address, phoneNumber, UserRole.PATIENT);
        this.points = points;
        this.penalties = penalties;
        this.subscriptions = subscriptions;
        this.myExaminations = myExaminations;
        this.allergies = allergies;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Long> getPenalties() {
        return penalties;
    }

    public void setPenalties(List<Long> penalties) {
        this.penalties = penalties;
    }

    public List<Long> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Long> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Long> getMyExaminations() {
        return myExaminations;
    }

    public void setMyExaminations(List<Long> myExaminations) {
        this.myExaminations = myExaminations;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getReservations() {
        return reservations;
    }

    public void setReservations(List<String> reservations) {
        this.reservations = reservations;
    }
}
