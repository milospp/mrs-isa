package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
@Entity
public class MedicineOrder {
    @Id
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;

    @OneToOne
    private Offer chosenOffer;
    @OneToMany
    private List<Offer> allOffer;
    @ManyToOne
    private Pharmacy pharmacy;
    @OneToMany
    private List<MedicineQuantity> allMedicines;
    @ManyToOne
    private PharmacyAdmin author; // jer ponudu za neku narudzbenicu sme da prihvati samo admin koji je napravio narudzbenicu

    public MedicineOrder() {
        this.allMedicines = new ArrayList<>();
        this.allOffer = new ArrayList<>();
    }

    public MedicineOrder(LocalDateTime startDate, LocalDateTime endDate, Offer chosenOffer,
                     List<Offer> allOffer, Pharmacy pharmacy, List<MedicineQuantity> allMedicines, PharmacyAdmin author) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.chosenOffer = chosenOffer;
        this.allOffer = allOffer;
        this.pharmacy = pharmacy;
        this.allMedicines = allMedicines;
        this.author = author;
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

    public PharmacyAdmin getAuthor() {
        return author;
    }

    public void setAuthor(PharmacyAdmin author) {
        this.author = author;
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
