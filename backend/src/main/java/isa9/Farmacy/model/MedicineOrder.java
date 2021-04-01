package isa9.Farmacy.model;

import java.time.LocalDateTime;
import java.util.*;

public class MedicineOrder {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Offer chosenOffer;
    private List<Offer> allOffer;
    private Pharmacy pharmacy;
    private List<MedicineQuantity> allMedicines;

    public MedicineOrder() {
        this.allMedicines = new ArrayList<>();
        this.allOffer = new ArrayList<>();
    }

    public MedicineOrder(LocalDateTime startDate, LocalDateTime endDate, Offer chosenOffer,
                     List<Offer> allOffer, Pharmacy pharmacy, List<MedicineQuantity> allMedicines) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.chosenOffer = chosenOffer;
        this.allOffer = allOffer;
        this.pharmacy = pharmacy;
        this.allMedicines = allMedicines;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Offer getChosenOffer() {
        return chosenOffer;
    }

    public void setChosenOffer(Offer chosenOffer) {
        this.chosenOffer = chosenOffer;
        this.chosenOffer.setStatus(OfferStatus.ACCEPTED);
    }

    public List<Offer> getAllOffer() {
        return allOffer;
    }

    public void setAllOffer(List<Offer> allOffer) {
        this.allOffer = allOffer;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public List<MedicineQuantity> getAllMedicines() {
        return allMedicines;
    }

    public void setAllMedicines(List<MedicineQuantity> allMedicines) {
        this.allMedicines = allMedicines;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, chosenOffer, allOffer, pharmacy, allMedicines);
    }

    @Override
    public String toString() {
        return "MedicineOrder{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", chosenOffer=" + chosenOffer +
                ", allOffer=" + allOffer +
                ", pharmacy=" + pharmacy +
                ", allMedicines=" + allMedicines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineOrder that = (MedicineOrder) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(chosenOffer, that.chosenOffer) && Objects.equals(allOffer, that.allOffer) && Objects.equals(pharmacy, that.pharmacy) && Objects.equals(allMedicines, that.allMedicines);
    }
}
