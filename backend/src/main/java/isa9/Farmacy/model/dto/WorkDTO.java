package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;

import java.time.LocalTime;

public class WorkDTO {
    private Long id;
    private Long doctorId;
    private Long pharmacyId;
    private String pharmacyName;
    private Address pharmacyAddr;
    private String pharmacyDesc;
    private LocalTime startHour;
    private LocalTime endHour;

    public WorkDTO(){

    }

    public WorkDTO(Long id, Long doctorId, Long pharmacyId, String pharmacyName, Address pharmacyAddr, String pharmacyDesc, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.doctorId = doctorId;
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddr = pharmacyAddr;
        this.pharmacyDesc = pharmacyDesc;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public Address getPharmacyAddr() {
        return pharmacyAddr;
    }

    public void setPharmacyAddr(Address pharmacyAddr) {
        this.pharmacyAddr = pharmacyAddr;
    }

    public String getPharmacyDesc() {
        return pharmacyDesc;
    }

    public void setPharmacyDesc(String pharmacyDesc) {
        this.pharmacyDesc = pharmacyDesc;
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
