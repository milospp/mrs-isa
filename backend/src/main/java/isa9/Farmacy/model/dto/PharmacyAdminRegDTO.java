package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;

public class PharmacyAdminRegDTO {
    private String name, surname, email, password, phoneNumber;
    private Address address;
    private Long pharmacyId;

    public PharmacyAdminRegDTO(String name, String surname, String email, String password,
                               String phoneNumber, Address address, Long pharmacyId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pharmacyId = pharmacyId;
    }

    public PharmacyAdminRegDTO(){

    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    public Long getPharmacyId() { return this.pharmacyId; }

    public void setPharmacyId(Long pharmacyId) { this.pharmacyId = pharmacyId; }
}
