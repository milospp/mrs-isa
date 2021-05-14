package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public abstract class OfferServiceBase implements OfferService {

    protected MedQuantityService medQuantityService;

    @Autowired
    public final void setMedQuantityService(MedQuantityService medQuantityService){ this.medQuantityService = medQuantityService;}

    @Override
    public void saveNewOffer(Offer offer) {
        Supplier supplier = offer.getSupplier();
        supplier.getAllOffer().add(offer);
        offer.getOrder().getAllOffer().add(offer);
        HashMap<String, MedicineAtSupplier> supplierInventory = new HashMap<>();

        List<MedicineQuantity> medicinesInOrder = offer.getOrder().getAllMedicines();
        for(MedicineAtSupplier mas : supplier.getMedicinesInStock()){
            supplierInventory.put(mas.getQuantity().getMedicine().getCode(), mas);
        }

        for(MedicineQuantity mq : medicinesInOrder){
            this.medQuantityService.reduceQuantity(supplierInventory.get(mq.getMedicine().getCode()).getQuantity(), mq.getQuantity());
        }

        offer.setStatus(OfferStatus.INDEFINITELY);
        offer.setStartDate(LocalDateTime.now());

        System.out.println(offer);

        this.save(offer);
    }
}
