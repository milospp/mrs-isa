package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.TypeOfReview;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class AppointmentDTO {
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startTime;
    private double price;
    private int durationInMins;
    private TypeOfReview type;

    private Doctor doctor;
    private Pharmacy pharmacy;
    private Examination examination;

    public AppointmentDTO() {
        super();
    }

    public AppointmentDTO(Long id, LocalDateTime startTime, double price, int durationInMins, TypeOfReview type, Doctor doctor, Pharmacy pharmacy, Examination examination) {
        this.id = id;
        this.startTime = startTime;
        this.price = price;
        this.durationInMins = durationInMins;
        this.type = type;
        this.doctor = doctor;
        this.pharmacy = pharmacy;
        this.examination = examination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(int durationInMins) {
        this.durationInMins = durationInMins;
    }

    public TypeOfReview getType() {
        return type;
    }

    public void setType(TypeOfReview type) {
        this.type = type;
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

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
