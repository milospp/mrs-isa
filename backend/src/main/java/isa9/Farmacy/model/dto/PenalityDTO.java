package isa9.Farmacy.model.dto;

import java.time.LocalDate;

public class PenalityDTO {
    private Long id;
    private String reason;
    private LocalDate date;

    public PenalityDTO() {
    }

    public PenalityDTO(Long id, String reason, LocalDate date) {
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
}
