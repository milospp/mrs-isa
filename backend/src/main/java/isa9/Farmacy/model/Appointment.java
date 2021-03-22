package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class Appointment {
    private Date startTime;
    private double price;
    private int durationInMins;

    public Appointment() {
        super();
    }

    public Appointment(Date startTime, double price, int durationInMins) {
        super();
        this.startTime = startTime;
        this.price = price;
        this.durationInMins = durationInMins;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
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

    @Override
    public String toString() {
        return "Appointment [startTime=" + startTime + ", price=" + price + ", durationInMins=" + durationInMins + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Double.compare(that.price, price) == 0 && durationInMins == that.durationInMins && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, price, durationInMins);
    }
}
