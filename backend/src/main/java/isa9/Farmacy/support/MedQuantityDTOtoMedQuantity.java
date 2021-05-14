package isa9.Farmacy.support;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import isa9.Farmacy.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedQuantityDTOtoMedQuantity  implements Converter<MedicineQuantityDTO, MedicineQuantity> {
    private MedicineService medicineService;

    @Autowired
    public MedQuantityDTOtoMedQuantity(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Override
    public MedicineQuantity convert(MedicineQuantityDTO quaDTO) {
         MedicineQuantity povratna = new MedicineQuantity();
         povratna.setQuantity(quaDTO.getQuantity());
         for (Medicine m : medicineService.findAll())
             if (m.getCode().equals(quaDTO.getMedicine().getCode())) {
                 povratna.setMedicine(m);
                 break;
             }
         return povratna;
    }

    public List<MedicineQuantity> convert(List<MedicineQuantityDTO> medicineQuantityDTOS) {
        List<MedicineQuantity> povratna = new ArrayList<>();
        for (MedicineQuantityDTO mq : medicineQuantityDTOS){
            povratna.add(convert(mq));
        }
        return povratna;
    }

}
