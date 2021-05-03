package isa9.Farmacy.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double price;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private String offerDescription;
    @ManyToOne
    private Supplier supplier;
    @OneToOne(fetch = FetchType.EAGER)
    private MedicineOrder order;
    @Enumerated
    private OfferStatus status;




    public Offer(double price, Date startDate, Date endDate, String offerDescription, Supplier sup) {
        super();
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerDescription = offerDescription;
        this.supplier = sup;
        this.status = OfferStatus.IDEFINITELY;
    }


}
