package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;

import java.time.LocalTime;

public class WorkDTO {
    private Long id;
    private Long doctorId;
    private Long pharmacyId;
    private LocalTime startHour;
    private LocalTime endHour;

    public WorkDTO(){

    }

    public WorkDTO(Long id, Long doctor, Long pharmacy, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.doctorId = doctor;
        this.pharmacyId = pharmacy;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
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
}
