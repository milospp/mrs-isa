package isa9.Farmacy.support;

import isa9.Farmacy.model.MedicineAtSupplier;
import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.dto.MedAtSupplierDTO;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MedicineAtSupplierToMedAtSupplierDTO implements Converter<MedicineAtSupplier , MedAtSupplierDTO> {
    private MedicineToMedicineDTO medicineToMedicineDTO;
    private SupplierToSupplierDTO supplierToSupplierDTO;

    @Autowired
    public MedicineAtSupplierToMedAtSupplierDTO(MedicineToMedicineDTO medicineToMedicineDTO, SupplierToSupplierDTO supplierToSupplierDTO){
        this.medicineToMedicineDTO = medicineToMedicineDTO;
        this.supplierToSupplierDTO = supplierToSupplierDTO;

    };

    @Override
    public MedAtSupplierDTO convert(MedicineAtSupplier medicineAtSupplier) {
        MedAtSupplierDTO masDTO = new MedAtSupplierDTO();

        masDTO.setMedicine(this.medicineToMedicineDTO.convert(medicineAtSupplier.getQuantity().getMedicine()));
        masDTO.setSupplier(this.supplierToSupplierDTO.convert(medicineAtSupplier.getSupplier()));
        masDTO.setId(medicineAtSupplier.getId());
        masDTO.setInStock(medicineAtSupplier.getQuantity().getQuantity());
        masDTO.setCurrentPrice(medicineAtSupplier.getSupplierPrice().getPrice());

        return masDTO;
    }

    public Map<String, MedAtSupplierDTO> setToMap(Set<MedicineAtSupplier> mases){
        HashMap<String, MedAtSupplierDTO> map = new HashMap<>();

        for(MedicineAtSupplier mas : mases){
            map.put(mas.getQuantity().getMedicine().getCode(), this.convert(mas));
        }

        return map;
    }
}
