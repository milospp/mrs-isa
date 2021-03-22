package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class MedReservation {
    private String id;
    private Date reservationDate;
    private Date lastDate;
    private boolean taken;
    private Pharmacist issued;

    public MedReservation() {
        super();
    }

    public MedReservation(String id, Date reservationDate, Date lastDate, boolean taken, Pharmacist issued) {
        super();
        this.id = id;
        this.reservationDate = reservationDate;
        this.lastDate = lastDate;
        this.taken = taken;
        this.issued = issued;
    }

    @Override
    public String toString() {
        return "MedReservation [id=" + id + ", reservationDate=" + reservationDate + ", lastDate=" + lastDate
                + ", Taken=" + this.taken + ", issued=" + issued + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return this.taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public Pharmacist getIssued() {
        return issued;
    }

    public void setIssued(Pharmacist issued) {
        this.issued = issued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedReservation that = (MedReservation) o;
        return taken == that.taken && Objects.equals(id, that.id) && Objects.equals(reservationDate, that.reservationDate) && Objects.equals(lastDate, that.lastDate) && Objects.equals(issued, that.issued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationDate, lastDate, taken, issued);
    }
}
