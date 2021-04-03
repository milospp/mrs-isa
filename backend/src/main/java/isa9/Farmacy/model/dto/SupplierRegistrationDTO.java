package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.UserRole;

public class SupplierRegistrationDTO extends UserDTO{
    private Long id;
    private String name, surname, phoneNumber, password, email;
    private Address address;

    public SupplierRegistrationDTO(Long id, String name, String surname, Address address,
                                   String phoneNumber, UserRole role, String email, String password) {
        super(id, name, surname, address, phoneNumber, role, email);
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public String getSurname() { return surname; }

    @Override
    public void setSurname(String surname) { this.surname = surname; }

    @Override
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String getEmail() { return email; }

    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public Address getAddress() { return address; }

    @Override
    public void setAddress(Address address) { this.address = address; }
}
