package isa9.Farmacy.model;

import java.util.Map;
import java.util.Objects;

public class Examination {

    private Patient patient;
    private User doctor; // ?
    private Appointment appointment;
    private ExaminationStatus status; // held, not held or canceled
    private String examinationInfo;
    private String diagnose;
    private Map<Medicine, Integer> therapy; // maybe should be the code of the medicine
    private TypeOfReview type;

    public Examination() {
    }

    public Examination(Patient patient, User doctor, Appointment appointment, ExaminationStatus status, String examinationInfo, String diagnose, Map<Medicine, Integer> therapy, TypeOfReview type) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
        this.status = status;
        this.examinationInfo = examinationInfo;
        this.diagnose = diagnose;
        this.therapy = therapy;
        this.type = type;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
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

    public Map<Medicine, Integer> getTherapy() {
        return therapy;
    }

    public void setTherapy(Map<Medicine, Integer> therapy) {
        this.therapy = therapy;
    }

    public TypeOfReview getType() {
        return type;
    }

    public void setType(TypeOfReview type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Examination{" +
                "patient=" + patient +
                ", doctor=" + doctor +
                ", appointment=" + appointment +
                ", status=" + status +
                ", examinationInfo='" + examinationInfo + '\'' +
                ", diagnose='" + diagnose + '\'' +
                ", therapy=" + therapy +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Examination that = (Examination) o;
        return Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor) && Objects.equals(appointment, that.appointment) && status == that.status && Objects.equals(examinationInfo, that.examinationInfo) && Objects.equals(diagnose, that.diagnose) && Objects.equals(therapy, that.therapy) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, doctor, appointment, status, examinationInfo, diagnose, therapy, type);
    }
}
