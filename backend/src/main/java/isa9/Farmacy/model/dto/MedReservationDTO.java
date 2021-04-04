package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Pharmacist;
import isa9.Farmacy.model.Pharmacy;

import java.util.Date;

public class MedReservationDTO {
    private String id;
    private Date reservationDate;
    private Date lastDate;
    private boolean taken;

    private String medicine;
    private int quantity;

    private PharmacistDTO issued;
    private PharmacyDTO pharmacy;

    public MedReservationDTO() {
        super();
    }

    public MedReservationDTO(String id, Date reservationDate, Date lastDate, boolean taken, String medicine, int quantity, PharmacistDTO issued, PharmacyDTO pharmacy) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.lastDate = lastDate;
        this.taken = taken;
        this.medicine = medicine;
        this.quantity = quantity;
        this.issued = issued;
        this.pharmacy = pharmacy;
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
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PharmacistDTO getIssued() {
        return issued;
    }

    public void setIssued(PharmacistDTO issued) {
        this.issued = issued;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public String toString() {
        return "MedReservationDTO{" +
                "id='" + id + '\'' +
                ", reservationDate=" + reservationDate +
                ", lastDate=" + lastDate +
                ", taken=" + taken +
                ", medicine='" + medicine + '\'' +
                ", quantity=" + quantity +
                ", issued=" + issued +
                ", pharmacy=" + pharmacy +
                '}';
    }
}
