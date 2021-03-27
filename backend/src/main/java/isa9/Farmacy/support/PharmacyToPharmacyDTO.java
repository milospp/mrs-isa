package isa9.Farmacy.support;


import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.PharmacyDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PharmacyToPharmacyDTO implements Converter<Pharmacy, PharmacyDTO> {

    @Override
    public PharmacyDTO convert(Pharmacy pharmacy) {
        PharmacyDTO dto = new PharmacyDTO();

        dto.setId(pharmacy.getId());
        dto.setName(pharmacy.getName());
        dto.setAddress(pharmacy.getAddress());
        dto.setDescription(pharmacy.getDescription());

        return dto;
    }

    public List<PharmacyDTO> convert(List<Pharmacy> pharmacies) {
        List<PharmacyDTO> dto = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacies){
            dto.add(convert(pharmacy));
        }
        return dto;
    }
    public List<PharmacyDTO> convert(Set<Pharmacy> pharmacies) {
        List<PharmacyDTO> dto = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacies){
            dto.add(convert(pharmacy));
        }
        return dto;
    }

}
