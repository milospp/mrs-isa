package isa9.Farmacy.support;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedAtSupplierDTO;
import isa9.Farmacy.model.dto.MedicineDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MedAtSupplierDTOtoMedAtSupplier implements Converter<MedAtSupplierDTO, MedicineAtSupplier> {

    private UserService userService;
    private MedicineService medicineService;

    @Autowired
    public MedAtSupplierDTOtoMedAtSupplier(UserService userService, MedicineService medicineService) {
        this.userService = userService;
        this.medicineService = medicineService;
    }

    @Override
    public MedicineAtSupplier convert(MedAtSupplierDTO medAtSupplierDTO) {
        MedicineAtSupplier medicineAtSupplier = new MedicineAtSupplier();

//        medicineAtSupplier.setSupplier((Supplier) userService.findOne(medAtSupplierDTO.getSupplier().getId()));
        medicineAtSupplier.setId(medAtSupplierDTO.getId());
        medicineAtSupplier.setQuantity(new MedicineQuantity(
                0L, this.medicineService.findOne(medAtSupplierDTO.getMedicine().getId()), medAtSupplierDTO.getInStock()));
        medicineAtSupplier.setSupplierPrice(new SupplierMedPrice(
                0L, LocalDateTime.now(),  medAtSupplierDTO.getCurrentPrice(), medicineAtSupplier
        ));


        return medicineAtSupplier;
    }

    public List<MedicineAtSupplier> convert(Collection<MedAtSupplierDTO> medicines) {
        List<MedicineAtSupplier> mases = new ArrayList<>();
        for (MedAtSupplierDTO masDTO : medicines){
            mases.add(convert(masDTO));
        }
        return mases;
    }
}
