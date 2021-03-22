package isa9.Farmacy.model;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class MedicineOrder {private Map<Medicine, Integer> medicines;
    private OrderStatus status;
    private Date deadLine;
    public MedicineOrder() {
        super();
    }

    public MedicineOrder(Map<Medicine, Integer> medicines, OrderStatus status, Date deadLine) {
        super();
        this.medicines = medicines;
        this.status = status;
        this.deadLine = deadLine;
    }

    public Map<Medicine, Integer> getMedicines() {
        return medicines;
    }

    public void setMedicines(Map<Medicine, Integer> medicines) {
        this.medicines = medicines;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return "MedicineOrder [medicines=" + medicines + ", status=" + status + ", deadLine=" + deadLine + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineOrder that = (MedicineOrder) o;
        return Objects.equals(medicines, that.medicines) && status == that.status && Objects.equals(deadLine, that.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicines, status, deadLine);
    }
}
