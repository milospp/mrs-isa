package isa9.Farmacy.support;

import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class OfferDTOtoOffer implements Converter<OfferDTO, Offer> {

    private OfferService offerService;

    @Autowired
    public OfferDTOtoOffer(OfferService offerService) {this.offerService = offerService;}

    @Override
    public Offer convert(OfferDTO offerDTO) {
        Offer povratna = null;
        for (Offer o : this.offerService.findAll())
            if (o.getOrder().getId() == offerDTO.getOrder() && o.getPrice() == offerDTO.getPrice()) {
                povratna = o;
                break;
            }
        return povratna;
    }

    public List<Offer> convert(List<OfferDTO> offerDTOS) {
        List<Offer> povratna = new ArrayList<>();
        for (OfferDTO o : offerDTOS) povratna.add(convert(o));
        return povratna;
    }
}
