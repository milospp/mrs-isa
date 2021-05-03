package isa9.Farmacy.service;

import isa9.Farmacy.model.Offer;

import java.util.List;

public interface OfferService extends GenericService<Offer> {

    List<Offer> getOffers(Long odrerId);
}
