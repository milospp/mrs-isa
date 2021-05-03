package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.Offer;
import isa9.Farmacy.repository.OrderRepository;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.impl.base.OrderServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return this.orderRepository.findAll();
    }

    @Override
    public MedicineOrder findOne(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineOrder save(MedicineOrder entity) {
        for (MedicineQuantity mq : entity.getAllMedicines()) this.medQuantityService.save(mq);
        for (Offer o : entity.getAllOffer()) this.offerService.save(o);
        return this.orderRepository.save(entity);
    }

    @Override
    public List<MedicineOrder> getAdminOrders(Long idAdmina) {
        List<MedicineOrder> povratna = new ArrayList<>();
        for (MedicineOrder mo : this.orderRepository.findAll()) if (mo.getAuthor().getId() == idAdmina) povratna.add(mo);
        return povratna;
    }

    @Override
    public void delete(MedicineOrder zaBrisanje) {
        this.orderRepository.delete(zaBrisanje);
    }
}
