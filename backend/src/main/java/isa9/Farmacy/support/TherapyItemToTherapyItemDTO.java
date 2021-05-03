package isa9.Farmacy.support;

import isa9.Farmacy.model.TherapyItem;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.TherapyItemDTO;
import isa9.Farmacy.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TherapyItemToTherapyItemDTO implements Converter<TherapyItem, TherapyItemDTO> {

    MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;

    @Autowired
    TherapyItemToTherapyItemDTO(MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO){
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
    }

    @Override
    public TherapyItemDTO convert(TherapyItem therapyItem) {
        TherapyItemDTO dto = new TherapyItemDTO();
        dto.setDays(therapyItem.getDays());
        dto.setMedInPharma(medicineInPharmacyToMedInPharmaDTO.convert(therapyItem.getMedicine()));

        return dto;
    }

    public HashSet<TherapyItemDTO> convert(Set<TherapyItem> therapyItems) {
        HashSet<TherapyItemDTO> set = new HashSet<>();
        for (TherapyItem therapyItem : therapyItems) {
            set.add(this.convert(therapyItem));
        }

        return set;
    }
}
