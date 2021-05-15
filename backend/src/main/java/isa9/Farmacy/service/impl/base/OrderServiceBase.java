package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.support.MedOrderDTOtoMedOrder;
import isa9.Farmacy.support.MedOrderToMedOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class OrderServiceBase implements OrderService {

    protected MedQuantityService medQuantityService;
    protected OfferService offerService;

    @Autowired
    public void setMedQuantityService(MedQuantityService medQuantityService) {
        this.medQuantityService = medQuantityService;
    }

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @Override
    public List<MedicineOrder> getAvailableOrders() {
        ArrayList<MedicineOrder> availableOrders = new ArrayList<>();
        List<MedicineOrder> allOrders = this.findAll();

        for(MedicineOrder mo : allOrders){
            if(mo.getChosenOffer() == null && LocalDateTime.now().isBefore(mo.getEndDate())){
                availableOrders.add(mo);
            }
        }

        return availableOrders;
    }
}
