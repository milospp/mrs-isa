package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;

public class PharmacyDTO {

    private Long id;
    private String name;
    private Address address;
    private String description;

    public PharmacyDTO() {
    }

    public PharmacyDTO(Long id, String name, String description, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PharmacyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", description='" + description + '\'' +
                '}';
    }
}
