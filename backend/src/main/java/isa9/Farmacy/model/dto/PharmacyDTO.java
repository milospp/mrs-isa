package isa9.Farmacy.model.dto;

import isa9.Farmacy.model.Address;

public class PharmacyDTO {

    private String name, description;
    private Address address;
    private Long id;

    public PharmacyDTO(String name, String description, Long id, Address address) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }
}
