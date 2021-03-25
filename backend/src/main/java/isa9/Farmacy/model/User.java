package isa9.Farmacy.model;

public abstract class User {

    private Address address;
    private String phoneNumber;
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;

    public User() {
    }

    public User(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password, String address, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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
        return "User: " + name + " " + surname + " " + email + " " + password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
