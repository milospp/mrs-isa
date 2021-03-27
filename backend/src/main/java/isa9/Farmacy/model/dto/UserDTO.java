package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.UserRole;

public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private Address address; // pretvori se u string?
    private String phoneNumber;
    private UserRole role;

    public UserDTO() {
    }

    @Deprecated
    public UserDTO(Long id, String name, String surname, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = new Address();
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(Long id, String name, String surname, Address address, String phoneNumber, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
