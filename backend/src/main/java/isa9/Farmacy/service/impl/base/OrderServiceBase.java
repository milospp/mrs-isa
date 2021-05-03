package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderServiceBase implements OrderService {

    protected MedQuantityService medQuantityService;
    protected OfferService offerService;

    @Autowired
    public void setMedQuantityService(MedQuantityService medQuantityService) {
        this.medQuantityService = medQuantityService;
    }

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
}
