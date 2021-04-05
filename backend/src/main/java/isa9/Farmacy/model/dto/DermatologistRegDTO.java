package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;

public class DermatologistRegDTO {
    private Long id;
    private String name, surname, phoneNumber, password, email;
    private Address address;

    public DermatologistRegDTO(){

    }

    public DermatologistRegDTO(Long id, String name, String surname,
                               String phoneNumber, String password, String email, Address address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }
}
