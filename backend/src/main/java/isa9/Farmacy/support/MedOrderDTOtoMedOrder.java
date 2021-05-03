package isa9.Farmacy.support;

import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class MedOrderDTOtoMedOrder implements Converter<MedicineOrderDTO, MedicineOrder> {

    private OrderService orderService;

    @Autowired
    public MedOrderDTOtoMedOrder(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public MedicineOrder convert(MedicineOrderDTO medicineOrderDTO) {
        MedicineOrder povratna = null;
        for (MedicineOrder m : orderService.findAll())
            if (m.getStartDate().equals(medicineOrderDTO.getStartDate()) &&
                m.getEndDate().equals((medicineOrderDTO.getEndDate())) &&
                m.getAuthor().getName().equals(medicineOrderDTO.getAuthor().getName()) &&
                m.getAuthor().getSurname().equals(medicineOrderDTO.getAuthor().getSurname())) {
                int broj = 0;
                for (MedicineQuantity mq : m.getAllMedicines()) {
                    if (mq.getQuantity() != medicineOrderDTO.getAllMedicines().get(broj).getQuantity() ||
                            !mq.getMedicine().getName().equals(
                                    medicineOrderDTO.getAllMedicines().get(broj).getMedicine().getName())) {
                        broj = -1;
                        break;
                    }
                    broj += 1;
                }
                if (broj == -1) continue;
                povratna = m;
                break;
            }
        return povratna;
    }
}
