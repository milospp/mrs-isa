package isa9.Farmacy.support;

import isa9.Farmacy.model.Pharmacist;
import isa9.Farmacy.model.dto.PharmacistDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PharmacistToPharmacistDTO implements Converter<Pharmacist, PharmacistDTO> {

    @Override
    public PharmacistDTO convert(Pharmacist farmaceut) {
        PharmacistDTO dto = new PharmacistDTO();

        dto.setId(farmaceut.getId());
        dto.setName(farmaceut.getName());
        dto.setSurname(farmaceut.getSurname());
        dto.setAddress(farmaceut.getAddress());
        dto.setPhoneNumber(farmaceut.getPhoneNumber());
        dto.setPharmacy(farmaceut.getWorking().get(0).getPharmacy());
        return dto;
    }

    public List<PharmacistDTO> convert(List<Pharmacist> farmaceuti) {
        List<PharmacistDTO> dto = new ArrayList<>();
        for (Pharmacist f : farmaceuti){
            dto.add(convert(f));
        }
        return dto;
    }
}
