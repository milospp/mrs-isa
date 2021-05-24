package isa9.Farmacy.constants;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.MedReservationStatus;

import java.time.LocalDate;

public class MedReservationConstants {
    public static final Long DB_ID = 1L;
    public static final String DB_CODE = "CODE123";
    public static final LocalDate DB_RES_DATE = LocalDate.of(2021,6,6);
    public static final LocalDate DB_LAST_DATE = LocalDate.of(2021,6,10);
    public static final MedReservationStatus DB_STATUS = MedReservationStatus.TAKEN;
    public static final int DB_QUANTITY = 2;



    public static final Long NEW_DB_ID = 2L;
    public static final String NEW_DB_CODE = "CODE222";
    public static final LocalDate NEW_DB_RES_DATE = LocalDate.of(2021,6,7);
    public static final LocalDate NEW_DB_LAST_DATE = LocalDate.of(2021,6,10);
    public static final MedReservationStatus NEW_DB_STATUS = MedReservationStatus.PENDING;
    public static final int NEW_DB_QUANTITY = 1;


    public static MedReservation getTakenExample() {
        MedReservation medReservation = new MedReservation();
        medReservation.setId(1L);
        medReservation.setCode("CODE123");
        medReservation.setReservationDate(LocalDate.of(2021,6,6));
        medReservation.setLastDate(LocalDate.of(2021,6,10));
        medReservation.setStatus(MedReservationStatus.TAKEN);
        medReservation.setQuantity(2);
        return medReservation;
    }

}
