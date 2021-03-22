package isa9.Farmacy.model;

import java.util.Objects;

public class Address {

    private String street;
    private String number;
    private String city;
    private String state;

    public Address() {
    }

    public Address(String street, String number, String city, String state) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) && number.equals(address.number) &&
                city.equals(address.city) && state.equals(address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, city, state);
    }

    @Override
    public String toString() {
        return street + " " + number + ", " + city + ", " + state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
