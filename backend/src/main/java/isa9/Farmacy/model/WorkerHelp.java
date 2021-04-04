package isa9.Farmacy.model;

import java.util.Objects;

public class WorkerHelp {
    private Pharmacist registerData;
    private String startHour;
    private String endHour ;

    public WorkerHelp() {
    }

    public WorkerHelp(Pharmacist registerData, String startHour, String endHour) {
        this.registerData = registerData;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Pharmacist getRegisterData() {
        return registerData;
    }

    public void setRegisterData(Pharmacist registerData) {
        this.registerData = registerData;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerHelp that = (WorkerHelp) o;
        return Objects.equals(registerData, that.registerData) && Objects.equals(startHour, that.startHour) && Objects.equals(endHour, that.endHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerData, startHour, endHour);
    }

    @Override
    public String toString() {
        return "WorkerHelp{" +
                "registerData=" + registerData +
                ", startHour='" + startHour + '\'' +
                ", endHour='" + endHour + '\'' +
                '}';
    }
}
