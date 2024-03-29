package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MedicineOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;

    @OneToOne
    private Offer chosenOffer;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Offer> allOffer;
    @ManyToOne
    private Pharmacy pharmacy;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade =  CascadeType.ALL)
    private List<MedicineQuantity> allMedicines;
    @ManyToOne
    private PharmacyAdmin author; // jer ponudu za neku narudzbenicu sme da prihvati samo admin koji je napravio narudzbenicu



    public MedicineOrder(LocalDateTime startDate, LocalDateTime endDate, Offer chosenOffer,
                     List<Offer> allOffer, Pharmacy pharmacy, List<MedicineQuantity> allMedicines, PharmacyAdmin author) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.chosenOffer = chosenOffer;
        this.allOffer = allOffer;
        this.pharmacy = pharmacy;
        this.allMedicines = allMedicines;
        this.author = author;
    }


}
