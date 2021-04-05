package isa9.Farmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Penality {
    @Id
    private Long id;
    @Column
    private String reason;
    @Column
    private LocalDate date;

    public Penality() {
        super();
    }

    public Penality(String reason, LocalDate date) {
        super();
        this.reason = reason;
        this.date = date;
    }

    public Penality(Long id, String reason, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
