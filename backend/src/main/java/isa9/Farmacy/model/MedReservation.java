package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class MedReservation {
    @Id
    private String id;
    @ManyToOne
    private Patient patient;
    @Column
    private Date reservationDate;
    @Column
    private Date lastDate;
    @Column
    private boolean taken;

    @ManyToOne
    private Medicine medicine;
    @Column
    private int quantity;

    @ManyToOne
    private Pharmacist issued;
    @ManyToOne
    private Pharmacy pharmacy;

    public MedReservation() {
        super();
    }

    public MedReservation(String id, Patient patient, Date reservationDate, Date lastDate, boolean taken, Medicine medicine, int quantity, Pharmacist issued, Pharmacy pharmacy) {
        this.id = id;
        this.patient = patient;
        this.reservationDate = reservationDate;
        this.lastDate = lastDate;
        this.taken = taken;
        this.medicine = medicine;
        this.quantity = quantity;
        this.issued = issued;
        this.pharmacy = pharmacy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Pharmacist getIssued() {
        return issued;
    }

    public void setIssued(Pharmacist issued) {
        this.issued = issued;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public String toString() {
        return "MedReservation{" +
                "id='" + id + '\'' +
                ", patient=" + patient +
                ", reservationDate=" + reservationDate +
                ", lastDate=" + lastDate +
                ", taken=" + taken +
                ", medicine=" + medicine +
                ", quantity=" + quantity +
                ", issued=" + issued +
                ", pharmacy=" + pharmacy +
                '}';
    }
}
