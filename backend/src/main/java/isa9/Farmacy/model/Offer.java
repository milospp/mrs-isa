package isa9.Farmacy.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;
import org.apache.tomcat.jni.Local;

@Getter
@Setter
@ToString(exclude = {"order", "supplier"})
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
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column
    private String offerDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    private Supplier supplier;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MedicineOrder order;
    @Enumerated
    private OfferStatus status;




    public Offer(double price, LocalDateTime startDate, LocalDateTime endDate, String offerDescription, Supplier sup) {
        super();
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerDescription = offerDescription;
        this.supplier = sup;
        this.status = OfferStatus.INDEFINITELY;
    }


}
