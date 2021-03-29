package isa9.Farmacy.support;

import isa9.Farmacy.model.Dermatologist;
import isa9.Farmacy.model.dto.DermatologistDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DermatologistToDermatologistDTO implements Converter<Dermatologist, DermatologistDTO> {

    @Override
    public DermatologistDTO convert(Dermatologist dermatolog) {
        DermatologistDTO dto = new DermatologistDTO();

        dto.setId(dermatolog.getId());
        dto.setName(dermatolog.getName());
        dto.setSurname(dermatolog.getSurname());
        dto.setAddress(dermatolog.getAddress());
        dto.setPhoneNumber(dermatolog.getPhoneNumber());
        dto.setPharmacies(dermatolog.getPharmacies());
        return dto;
    }

    public List<DermatologistDTO> convert(List<Dermatologist> dermatolozi) {
        List<DermatologistDTO> dto = new ArrayList<>();
        for (Dermatologist d : dermatolozi){
            dto.add(convert(d));
        }
        return dto;
    }
}