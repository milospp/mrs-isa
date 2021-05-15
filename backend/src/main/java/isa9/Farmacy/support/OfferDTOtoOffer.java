package isa9.Farmacy.support;

import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.Supplier;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.UserService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OfferDTOtoOffer implements Converter<OfferDTO, Offer> {

    private OfferService offerService;
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OfferDTOtoOffer(OfferService offerService, OrderService orderService, UserService userService) {
        this.offerService = offerService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public Offer convert(OfferDTO offerDTO) {
        Offer offer = new Offer();

        offer.setStatus(offerDTO.getStatus());
        offer.setOfferDescription(offerDTO.getOfferDescription());
        offer.setPrice(offerDTO.getPrice());
        offer.setStartDate(offerDTO.getStartDate());
        offer.setEndDate(offerDTO.getEndDate());
        offer.setId(offerDTO.getId());
        offer.setOrder(this.orderService.findOne(offerDTO.getOrder()));
        offer.setSupplier((Supplier) this.userService.findOne(offerDTO.getSupplier().getId()));

        return offer;
    }

    public List<Offer> convert(List<OfferDTO> offerDTOS) {
        List<Offer> povratna = new ArrayList<>();
        for (OfferDTO o : offerDTOS) povratna.add(convert(o));
        return povratna;
    }
}
