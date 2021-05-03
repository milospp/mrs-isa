package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Offer;
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
        return this.save(entity);
    }

    @Override
    public List<Offer> getOffers(Long orderId) {
        List<Offer> povratna = new ArrayList<>();
        for (Offer o : this.offerRepository.findAll())
            if (o.getOrder().getId() == orderId)
                povratna.add(o);
        return povratna;
    }
}
