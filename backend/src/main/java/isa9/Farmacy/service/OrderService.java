package isa9.Farmacy.service;

import isa9.Farmacy.model.MedicineOrder;

import java.util.List;

public interface OrderService extends GenericService<MedicineOrder>  {

    List<MedicineOrder> getAdminOrders(Long idAdmina);
    void delete(MedicineOrder zaBrisanje);
}
