package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class Vacation {private boolean accepted;
    private TypeOfRest type;
    private Date startDate;
    private Date endDate;
    private String whyNot;
    public Vacation() {
        super();
    }

    public Vacation(boolean accepted, TypeOfRest type, Date startDate, Date endDate, String whyNot) {
        super();
        this.accepted = accepted;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.whyNot = whyNot;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public TypeOfRest getType() {
        return type;
    }

    public void setType(TypeOfRest type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWhyNot() {
        return whyNot;
    }

    public void setWhyNot(String whyNot) {
        this.whyNot = whyNot;
    }

    @Override
    public String toString() {
        return "Vacation [accepted=" + accepted + ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate
                + ", whyNot=" + whyNot + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacation vacation = (Vacation) o;
        return accepted == vacation.accepted && type == vacation.type && Objects.equals(startDate, vacation.startDate) && Objects.equals(endDate, vacation.endDate) && Objects.equals(whyNot, vacation.whyNot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accepted, type, startDate, endDate, whyNot);
    }
}
