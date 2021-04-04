package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.*;

import java.util.Map;
import java.util.Objects;

public class ExaminationDTO {

    private Long id;
    private PatientDTO patient;
    private Long appointment;
    private ExaminationStatus status; // held, not held or canceled
    private String examinationInfo;
    private String diagnose;
    private Map<String, Integer> therapy; // maybe should be the code of the medicine
    private TypeOfReview type;

    public ExaminationDTO() {
    }

    public ExaminationDTO(Long id, PatientDTO patient, Long appointment, ExaminationStatus status, String examinationInfo, String diagnose, Map<String, Integer> therapy, TypeOfReview type) {
        this.id = id;
        this.patient = patient;
        this.appointment = appointment;
        this.status = status;
        this.examinationInfo = examinationInfo;
        this.diagnose = diagnose;
        this.therapy = therapy;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public Long getAppointment() {
        return appointment;
    }

    public void setAppointment(Long appointment) {
        this.appointment = appointment;
    }

    public ExaminationStatus getStatus() {
        return status;
    }

    public void setStatus(ExaminationStatus status) {
        this.status = status;
    }

    public String getExaminationInfo() {
        return examinationInfo;
    }

    public void setExaminationInfo(String examinationInfo) {
        this.examinationInfo = examinationInfo;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public Map<String, Integer> getTherapy() {
        return therapy;
    }

    public void setTherapy(Map<String, Integer> therapy) {
        this.therapy = therapy;
    }

    public TypeOfReview getType() {
        return type;
    }

    public void setType(TypeOfReview type) {
        this.type = type;
    }
}
