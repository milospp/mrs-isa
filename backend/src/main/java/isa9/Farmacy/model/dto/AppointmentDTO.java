package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.ExaminationDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
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

    private DoctorDTO doctor;
    private PharmacyDTO pharmacy;
    private ExaminationDTO examination;

    public AppointmentDTO() {
        super();
    }

    public AppointmentDTO(Long id, LocalDateTime startTime, double price, int durationInMins, TypeOfReview type, DoctorDTO doctor, PharmacyDTO pharmacy, ExaminationDTO examination) {
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

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    public ExaminationDTO getExamination() {
        return examination;
    }

    public void setExamination(ExaminationDTO examination) {
        this.examination = examination;
    }
}
