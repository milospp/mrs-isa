package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.repository.MedQuantityRepository;
import isa9.Farmacy.service.MedQuantityService;
import isa9.Farmacy.service.impl.base.MedQuantityServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbMedQuantityService extends MedQuantityServiceBase implements MedQuantityService {

    private final MedQuantityRepository medQuantityRepository;

    @Autowired
    public dbMedQuantityService(MedQuantityRepository medQuantityRepository) {
        this.medQuantityRepository = medQuantityRepository;
    }

    @Override
    public List<MedicineQuantity> findAll() {
        return medQuantityRepository.findAll();
    }

    @Override
    public MedicineQuantity findOne(Long id) {
        return medQuantityRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineQuantity save(MedicineQuantity entity) {
        return medQuantityRepository.save(entity);
    }
}
