package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Offer {
    @Id
    private Long id;
    @Column
    private double price;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private String offerDescription;
    @ManyToOne
    private Supplier supplier;
    @OneToOne
    private MedicineOrder order;
    @Enumerated
    private OfferStatus status;


    public Offer() {
        super();
        this.status = OfferStatus.IDEFINITELY;
    }

    public Offer(double price, Date startDate, Date endDate, String offerDescription, Supplier sup) {
        super();
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerDescription = offerDescription;
        this.supplier = sup;
        this.status = OfferStatus.IDEFINITELY;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public MedicineOrder getOrder() {
        return order;
    }

    public void setOrder(MedicineOrder order) {
        this.order = order;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Offer [price=" + price + ", startDate=" + startDate + ", endDate=" + endDate + ", offerDescription="
                + offerDescription + ", supplier=" + supplier +  "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0 && Objects.equals(startDate, offer.startDate) &&
                Objects.equals(endDate, offer.endDate) && Objects.equals(offerDescription, offer.offerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, startDate, endDate, offerDescription);
    }
}
