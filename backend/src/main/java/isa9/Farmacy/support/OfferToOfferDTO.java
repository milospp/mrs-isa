package isa9.Farmacy.support;

import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.model.dto.SupplierDTO;
import isa9.Farmacy.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class OfferToOfferDTO implements Converter<Offer, OfferDTO> {
    @Override
    public OfferDTO convert(Offer offer) {
        OfferDTO povratna = new OfferDTO();
        povratna.setOrder(offer.getOrder().getId());
        povratna.setOfferDescription(offer.getOfferDescription());
        povratna.setStatus(offer.getStatus());
        povratna.setPrice(offer.getPrice());
        povratna.setEndDate(offer.getEndDate());
        povratna.setStartDate(offer.getStartDate());

        UserToUserDTO konverter = new UserToUserDTO();
        UserDTO pomocna = konverter.convert(offer.getSupplier());
        SupplierDTO zaPovatnu = new SupplierDTO();
        zaPovatnu.setAddress(pomocna.getAddress());
        zaPovatnu.setName(pomocna.getName());
        zaPovatnu.setSurname(pomocna.getSurname());
        zaPovatnu.setPhoneNumber(pomocna.getPhoneNumber());
        zaPovatnu.setRole(pomocna.getRole());
        povratna.setSupplier(zaPovatnu);

        return povratna;
    }

    public List<OfferDTO> convert(List<Offer> offers) {
        List<OfferDTO> povratna = new ArrayList<>();
        for (Offer o : offers) povratna.add(convert(o));
        return povratna;
    }
}
