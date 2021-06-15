package isa9.Farmacy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class MedReservationSearchDTO {

    private String name = "";
    private String pharmacy = "";

    @JsonFormat(pattern="yyyy-M-d")
    private LocalDate minResDate = null;
    @JsonFormat(pattern="yyyy-M-d")
    private LocalDate maxResDate = null;
    @JsonFormat(pattern="yyyy-M-d")
    private LocalDate minLastDate = null;
    @JsonFormat(pattern="yyyy-M-d")
    private LocalDate maxLastDate = null;
    private boolean onlye = false;


    private int minQuantity = 0;
    private int maxQuantity = 99999;
    private String sort = "RES_DATE_ASC";

}
