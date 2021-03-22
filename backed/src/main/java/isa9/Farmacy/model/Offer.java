package isa9.Farmacy.model;

import java.util.Date;
import java.util.Objects;

public class Offer {
    private double price;
    private Date startDate;
    private Date endDate;
    private String offerDescription;


    public Offer() {
        super();
    }

    public Offer(double price, Date startDate, Date endDate, String offerDescription) {
        super();
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerDescription = offerDescription;
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

    @Override
    public String toString() {
        return "Offer [price=" + price + ", startDate=" + startDate + ", endDate=" + endDate + ", offerDescription="
                + offerDescription + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0 && Objects.equals(startDate, offer.startDate) && Objects.equals(endDate, offer.endDate) && Objects.equals(offerDescription, offer.offerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, startDate, endDate, offerDescription);
    }
}
