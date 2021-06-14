package isa9.Farmacy.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private double longitude;
    @Column
    private double latitude;

    public Address(String street, String number, String city, String state) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
    }

    public Address(String street, String number, String city, String state, double longitude, double latitude) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
