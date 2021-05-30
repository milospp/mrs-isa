package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.service.MedInPharmaService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class MedInPharmaServiceBase implements MedInPharmaService {

    protected PharmacyService pharmacyService;

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }
}
