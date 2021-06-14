package isa9.Farmacy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import lombok.*;

@Getter
@Setter
@ToString(exclude = {"medicinesInStock", "allOffer"})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Supplier extends User{
    @OneToMany(fetch = FetchType.LAZY)
    private List<Offer> allOffer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicineAtSupplier> medicinesInStock;



    public Supplier(Long id, String name, String surname, String email, String password,
                    Address address, String phoneNumber, UserRole role, Timestamp t) {
        super(id, name, surname, email, password, address, phoneNumber, role, true, t);
        this.allOffer = new ArrayList<Offer>();
        this.medicinesInStock = new HashSet<MedicineAtSupplier>();
    }






}
