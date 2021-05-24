package isa9.Farmacy.service;

import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.dto.OfferSearchDTO;

import java.util.List;

public interface OfferService extends GenericService<Offer> {

    List<Offer> getOffers(Long odrerId);
    List<Offer> getOffersOfSupplier(Long supplierId);
    void saveNewOffer(Offer offer);
    List<Offer> filterOffers(List<Offer> offers, OfferSearchDTO offerSearchDTO);
    void updateOffer(Offer offer);
    List<Offer> getOffersForOrder(Long orderId);
    void acceptOffer(Offer o);
    void rejectOffer(Offer o);
}
