package isa9.Farmacy.model;

import java.util.Objects;

public class Complain {

    private User user;
    private Pharmacy pharmacy;
    private String description;


    public Complain() {
        super();
    }

    public Complain(User user, Pharmacy pharmacy, String description) {
        super();
        this.user = user;
        this.pharmacy = pharmacy;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Complain [user=" + user + ", pharmacy=" + pharmacy + ", Description=" + this.description + "]";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complain complain = (Complain) o;
        return Objects.equals(user, complain.user) && Objects.equals(pharmacy, complain.pharmacy) && Objects.equals(description, complain.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, pharmacy, description);
    }
}
