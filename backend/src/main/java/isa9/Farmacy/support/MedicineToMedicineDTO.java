package isa9.Farmacy.support;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedicineDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MedicineToMedicineDTO implements Converter<Medicine, MedicineDTO> {

    @Override
    public MedicineDTO convert(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();

        dto.setId(medicine.getId());
        dto.setCode(medicine.getCode());
        dto.setName(medicine.getName());
        dto.setStructure(medicine.getStructure());
        dto.setManufacturer(medicine.getManufacturer());
        dto.setNote(medicine.getNote());
        dto.setPoints(medicine.getPoints());
        dto.setShape(medicine.getShape());
        dto.setType(medicine.getType());
        dto.setPerscription(medicine.getPerscription());
        dto.setRating(medicine.getRating());


        dto.setReplacementMedicationIds(
                medicine.getReplacementMedication().stream()
                        .map(Medicine::getCode)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public List<MedicineDTO> convert(Collection<Medicine> medicines) {
        List<MedicineDTO> dto = new ArrayList<>();
        for (Medicine medicine : medicines){
            dto.add(convert(medicine));
        }
        return dto;
    }

}
