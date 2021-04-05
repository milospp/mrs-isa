package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class EPrescription {
    @Id
    private String code;
    @ManyToOne
    private Patient patient;
    @Column
    private Date issueDate;

    public EPrescription() {
        super();
    }

    public EPrescription(String code, Patient patient, Date issueDate) {
        super();
        this.code = code;
        this.patient = patient;
        this.issueDate = issueDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "EPrescription [code=" + code + ", Patient=" + this.patient + ", IssueDate=" + this.issueDate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EPrescription that = (EPrescription) o;
        return Objects.equals(code, that.code) && Objects.equals(patient, that.patient) && Objects.equals(issueDate, that.issueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, patient, issueDate);
    }
}
