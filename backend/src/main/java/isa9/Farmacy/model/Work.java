package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
public class Work {
    @Id
    private Long id;

    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Pharmacy pharmacy;
    @Column
    private LocalTime startHour;
    @Column
    private LocalTime endHour;

    public Work(){

    }

    public Work(Long id, Doctor doctor, Pharmacy pharmacy, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.doctor = doctor;
        this.pharmacy = pharmacy;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacist) {
        this.pharmacy = pharmacist;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
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
        return doctor + " " + pharmacy + " " + startHour + "-" + endHour;
    }
}
