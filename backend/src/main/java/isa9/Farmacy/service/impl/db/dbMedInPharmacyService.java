package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.repository.MedInPharmaRepository;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.service.MedInPharmaService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.impl.base.MedInPharmaServiceBase;
import isa9.Farmacy.service.impl.base.MedicineServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Component
@Primary
@Service
public class dbMedInPharmacyService extends MedInPharmaServiceBase implements MedInPharmaService {
    private final MedInPharmaRepository medInPharmaRepository;

    @Autowired
    public dbMedInPharmacyService(MedInPharmaRepository medInPharmaRepository) {
        this.medInPharmaRepository = medInPharmaRepository;
    }

    @Override
    public List<MedicineInPharmacy> findAll() {
        return this.medInPharmaRepository.findAll();
    }

    @Override
    public MedicineInPharmacy findOne(Long id) {
        return this.medInPharmaRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineInPharmacy save(MedicineInPharmacy entity) { return this.medInPharmaRepository.save(entity); }

    @Override
    public Collection<MedicineInPharmacy> findAllMedicinesInPharmacy(Long medicineId) {
        return medInPharmaRepository.findAllByMedicineId(medicineId);
    }
}
