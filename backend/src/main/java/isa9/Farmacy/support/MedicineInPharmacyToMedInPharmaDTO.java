package isa9.Farmacy.support;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.MedicineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MedicineInPharmacyToMedInPharmaDTO implements Converter<MedicineInPharmacy, MedInPharmaDTO> {

    private final MedicineToMedicineDTO converter;

    @Autowired
    public MedicineInPharmacyToMedInPharmaDTO(MedicineToMedicineDTO converter) {
        this.converter = converter;
    }

    @Override
    public MedInPharmaDTO convert(MedicineInPharmacy medicineInPharmacy) {

        MedInPharmaDTO dto = new MedInPharmaDTO();
        MedicineDTO medDto = converter.convert(medicineInPharmacy.getMedicine());

        dto.setId(medicineInPharmacy.getId());
        dto.setPharmacyId(medicineInPharmacy.getPharmacy().getId());
        dto.setMedicine(medDto);
        dto.setCurrentPrice(medicineInPharmacy.getCurrentPrice().getPrice());
        dto.setInStock(medicineInPharmacy.getInStock());

        return dto;
    }
}
