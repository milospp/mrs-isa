package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

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



}
