package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class Penality {
    private String reason;
    private Date date;

    public Penality() {
        super();
    }

    public Penality(String reason, Date date) {
        super();
        this.reason = reason;
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Penality [reason=" + reason + ", date=" + date + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penality penality = (Penality) o;
        return Objects.equals(reason, penality.reason) && Objects.equals(date, penality.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, date);
    }
}
