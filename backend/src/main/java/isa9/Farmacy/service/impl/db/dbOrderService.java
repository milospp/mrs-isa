package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.OfferStatus;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.repository.OrderRepository;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.impl.base.OrderServiceBase;
import isa9.Farmacy.support.OfferDTOtoOffer;
import isa9.Farmacy.support.OfferToOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Primary
@Service
public class dbOrderService extends OrderServiceBase implements OrderService {

    private final OrderRepository orderRepository;


    @Autowired
    public dbOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<MedicineOrder> findAll() {
        List<MedicineOrder> povratna = new ArrayList<>();
        for (MedicineOrder mo : this.orderRepository.findAll()) {
            List<Offer> ponude = this.offerService.getOffers(mo.getId());
            mo.setAllOffer(ponude);
            povratna.add(mo);
        }
        return povratna;
    }

    @Override
    public MedicineOrder findOne(Long id) {
//        MedicineOrder povratna = this.orderRepository.findById(id).orElse(null);
//        if (povratna == null) return povratna;
//        List<Offer> ponude = this.offerService.getOffers(povratna.getId());
//        povratna.setAllOffer(ponude);
//        return povratna;

        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineOrder save(MedicineOrder entity) {
        for (Offer o : entity.getAllOffer()) this.offerService.save(o);
        return this.orderRepository.save(entity);
    }

    @Override
    public List<MedicineOrder> getAdminOrders(Long idAdmina) {
        List<MedicineOrder> povratna = new ArrayList<>();
        for (MedicineOrder mo : this.orderRepository.findAll()) if (mo.getAuthor().getId() == idAdmina) {
            List<Offer> ponude = this.offerService.getOffers(mo.getId());
            mo.setAllOffer(ponude);
            povratna.add(mo);
        }
        return povratna;
    }

    @Override
    public void delete(MedicineOrder zaBrisanje) {
        this.orderRepository.delete(zaBrisanje);
    }

    @Override
    public int chooseOffer(Offer ponuda, Long idOrder) {
        int povratna = -1;
        MedicineOrder narudzbenica = this.orderRepository.findById(idOrder).orElse(null);

        if (ponuda.getEndDate().isBefore(LocalDateTime.now())) return povratna;
        povratna = 0;
        narudzbenica.setChosenOffer(ponuda);
        ponuda.setStatus(OfferStatus.ACCEPTED);
        for (Offer o : narudzbenica.getAllOffer()) if (o.getId() != ponuda.getId()) o.setStatus(OfferStatus.REJECTED);
        this.orderRepository.save(narudzbenica);
        return povratna;
    }
}
