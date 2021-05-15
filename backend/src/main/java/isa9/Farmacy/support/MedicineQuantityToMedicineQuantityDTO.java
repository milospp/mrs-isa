package isa9.Farmacy.support;

import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MedicineQuantityToMedicineQuantityDTO implements Converter<MedicineQuantity, MedicineQuantityDTO> {

    @Override
    public MedicineQuantityDTO convert(MedicineQuantity kolicinaLeka) {
        MedicineQuantityDTO dto = new MedicineQuantityDTO();
        dto.setQuantity(kolicinaLeka.getQuantity());
        dto.setMedicine((new MedicineToMedicineDTO()).convert(kolicinaLeka.getMedicine()));
        return dto;
    }

    public List<MedicineQuantityDTO> convert(List<MedicineQuantity> kolicinaLeka) {
        List<MedicineQuantityDTO> dto = new ArrayList<>();
        for (MedicineQuantity mq : kolicinaLeka){
            dto.add(convert(mq));
        }
        return dto;
    }

    public Map<String, MedicineQuantityDTO> listToMap(List<MedicineQuantity> medicineQuantities){
        HashMap<String, MedicineQuantityDTO> map = new HashMap<>();

        for(MedicineQuantity mq : medicineQuantities){
            map.put(mq.getMedicine().getCode(), this.convert(mq));
        }

        return map;
    }
}
