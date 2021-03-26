package isa9.Farmacy.model;

public class PharmacyAdmin extends User{

    private Pharmacy pharmacy;

    public PharmacyAdmin() {
    }

    public PharmacyAdmin(Long id, String name, String surname, String email, String password, Pharmacy pharmacy, Address address, String phoneNumber) {
        super(id, name, surname, email, password, address, phoneNumber);
        this.pharmacy = pharmacy;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "PharmacyAdmin: " + getName() + " " + getSurname() + " " + getEmail() + " " + getPassword() + " " + pharmacy;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}