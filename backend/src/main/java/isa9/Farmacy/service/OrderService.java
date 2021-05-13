package isa9.Farmacy.service;

import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.model.dto.OfferDTO;

import java.util.List;

public interface OrderService extends GenericService<MedicineOrder>  {

    List<MedicineOrder> getAdminOrders(Long idAdmina);
    void delete(MedicineOrder zaBrisanje);
    int chooseOffer(OfferDTO offerDTO);
    List<MedicineOrder> getAvailableOrders();
}
