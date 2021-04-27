package isa9.Farmacy.support;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.MedicineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MedicineInPharmacyToMedInPharmaDTO implements Converter<MedicineInPharmacy, MedInPharmaDTO> {

    private final MedicineToMedicineDTO converter;
    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;

    @Autowired
    public MedicineInPharmacyToMedInPharmaDTO(MedicineToMedicineDTO converter, PharmacyToPharmacyDTO pharmacyToPharmacyDTO) {
        this.converter = converter;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
    }

    @Override
    public MedInPharmaDTO convert(MedicineInPharmacy medicineInPharmacy) {

        MedInPharmaDTO dto = new MedInPharmaDTO();
        MedicineDTO medDto = converter.convert(medicineInPharmacy.getMedicine());

        dto.setId(medicineInPharmacy.getId());
        dto.setPharmacy(pharmacyToPharmacyDTO.convert(medicineInPharmacy.getPharmacy()));
        dto.setMedicine(medDto);
        dto.setCurrentPrice(medicineInPharmacy.getCurrentPrice().getPrice());
        dto.setInStock(medicineInPharmacy.getInStock());

        return dto;
    }

    public List<MedInPharmaDTO> convert(List<MedicineInPharmacy> medicineInPharmacy) {
        List<MedInPharmaDTO> dto = new ArrayList<>();
        for (MedicineInPharmacy mf : medicineInPharmacy) dto.add(this.convert(mf));
        return dto;
    }

    public List<MedInPharmaDTO> convert(Set<MedicineInPharmacy> medicineInPharmacy) {
        List<MedInPharmaDTO> dto = new ArrayList<>();
        for (MedicineInPharmacy mf : medicineInPharmacy) dto.add(this.convert(mf));
        return dto;
    }
}
