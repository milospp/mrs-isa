package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.OfferRepository;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.impl.base.OfferServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Service
public class dbOfferService extends OfferServiceBase implements OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public dbOfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> findAll() {
        return this.offerRepository.findAll();
    }

    @Override
    public Offer findOne(Long id) {
        return this.offerRepository.findById(id).orElse(null);
    }

    @Override
    public Offer save(Offer entity) {
        return this.offerRepository.save(entity);
    }

    @Override
    public List<Offer> getOffers(Long orderId) {
        return this.offerRepository.findAll();
    }

    @Override
    public List<Offer> getOffersForOrder(Long orderId) {
        List<Offer> povratna = new ArrayList<>();
        for (Offer o : this.offerRepository.findAll())
            if (o.getOrder().getId() == orderId)
                povratna.add(o);
        return povratna;
    }

    @Override
    public void acceptOffer(Offer o) {
        o.setStatus(OfferStatus.ACCEPTED);
        Pharmacy apoteka = o.getOrder().getPharmacy();
        for (MedicineQuantity mq : o.getOrder().getAllMedicines()) {
            for (MedicineInPharmacy mp : apoteka.getMedicines()) {
                if (mq.getMedicine().getCode() == mp.getMedicine().getCode()) {
                    mp.setInStock(mq.getQuantity() + mp.getInStock());
                    break;
                }
            }
        }
        this.pharmacyService.save(apoteka);
        this.offerRepository.save(o);
    }

    @Override
    public void rejectOffer(Offer o) {
        o.setStatus(OfferStatus.REJECTED);
        Supplier dobavljac = o.getSupplier();
        for (MedicineQuantity mq : o.getOrder().getAllMedicines()) {
            for (MedicineAtSupplier ms : dobavljac.getMedicinesInStock()) {
                if (mq.getMedicine().getCode() == ms.getQuantity().getMedicine().getCode()) {
                    ms.getQuantity().setQuantity(mq.getQuantity() + ms.getQuantity().getQuantity());
                    break;
                }
            }
        }
        this.userService.save(dobavljac);
        this.offerRepository.save(o);
    }
}
