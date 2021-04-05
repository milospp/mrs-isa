package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
public class Examination {
    @Id
    private Long id;
    @ManyToOne
    private Patient patient;
    @OneToOne
    private Appointment appointment;
    @Enumerated
    private ExaminationStatus status; // held, not held or canceled
    @Column
    private String examinationInfo;
    @Column
    private String diagnose;

    @ElementCollection
    @CollectionTable(name = "therapy",
            joinColumns = {@JoinColumn(name = "therapy_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "therapy_medicine")
    @Column
    private Map<Medicine, Integer> therapy; // maybe should be the code of the medicine

    public Examination() {
    }

    public Examination(Long id, Patient patient, Appointment appointment, ExaminationStatus status, String examinationInfo, String diagnose, Map<Medicine, Integer> therapy) {
        this.id = id;
        this.patient = patient;
        this.appointment = appointment;
        this.status = status;
        this.examinationInfo = examinationInfo;
        this.diagnose = diagnose;
        this.therapy = therapy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
}
