package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.OfferSearchDTO;
import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public abstract class OfferServiceBase implements OfferService {

    protected MedQuantityService medQuantityService;
    protected UserService userService;

    @Autowired
    public final void setMedQuantityService(MedQuantityService medQuantityService){ this.medQuantityService = medQuantityService;}

    @Autowired
    public final void setUserService(UserService userService){ this.userService = userService; }

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

    @Override
    public List<Offer> getOffersOfSupplier(Long supplierId) {
        Supplier supplier = (Supplier) this.userService.findOne(supplierId);
        return supplier.getAllOffer();
    }

    @Override
    public List<Offer> filterOffers(List<Offer> offers, OfferSearchDTO offerSearchDTO) {
        if (offerSearchDTO == null) return offers;
        final Map<String, Comparator<Offer>> critMap = new HashMap<String, Comparator<Offer>>();
        critMap.put("DESC_ASC", (o1,o2)->{return o1.getOfferDescription().trim().compareToIgnoreCase(o2.getOfferDescription().trim());});
        critMap.put("DESC_DES", (o2,o1)->{return o1.getOfferDescription().trim().compareToIgnoreCase(o2.getOfferDescription().trim());});
        critMap.put("STATUS_ASC", Comparator.comparing(Offer::getStatus));
        critMap.put("STATUS_DEC", Comparator.comparing(Offer::getStatus).reversed());
        critMap.put("PRICE_ASC", Comparator.comparingDouble(Offer::getPrice));
        critMap.put("PRICE_DES", Comparator.comparingDouble(Offer::getPrice).reversed());
        critMap.put("SDATE_ASC", Comparator.comparing(Offer::getStartDate));
        critMap.put("SDATE_DES", Comparator.comparing(Offer::getStartDate).reversed());
        critMap.put("EDATE_ASC", Comparator.comparing(Offer::getEndDate));
        critMap.put("EDATE_DES", Comparator.comparing(Offer::getEndDate).reversed());
        Comparator<Offer> comp = critMap.getOrDefault(offerSearchDTO.getSort().toUpperCase().trim(), critMap.values().iterator().next());



        return offers.stream()
                .filter(o -> offerSearchDTO.getDescription().isEmpty() || o.getOfferDescription().toLowerCase().contains(offerSearchDTO.getDescription().toLowerCase()))
                .filter(o -> offerSearchDTO.getStatus().equalsIgnoreCase("INDEFINITELY") || o.getStatus().toString().equalsIgnoreCase(offerSearchDTO.getStatus()))
                .filter(o -> o.getPrice() >= offerSearchDTO.getMinPrice() && o.getPrice() <= offerSearchDTO.getMaxPrice())
                .filter(o -> o.getStartDate().isAfter(offerSearchDTO.getStartDate()) && o.getStartDate().isBefore(o.getEndDate()))
                .sorted(comp).collect(Collectors.toList());
    }
}
