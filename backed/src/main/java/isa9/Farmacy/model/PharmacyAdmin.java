package isa9.Farmacy.model;

public class PharmacyAdmin extends User{

    private Pharmacy pharmacy;

    public PharmacyAdmin() {
    }

    public PharmacyAdmin(Long id, String username, String name, String surname, String email, String password, Pharmacy pharmacy) {
        super(id, username, name, surname, email, password);
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
        return "PharmacyAdmin: " + getUsername() + " " + getName() + " " + getSurname() + " " + getEmail() + " " + getPassword() + " " + pharmacy;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
