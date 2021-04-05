package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.repository.PharmacyRepository;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbPharmacyService extends PharmacyServiceBase implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public dbPharmacyService(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy findOne(Long id) {
        return this.pharmacyRepository.findById(id).orElseGet(null);
    }

    @Override
    public Pharmacy save(Pharmacy entity) {
        return this.pharmacyRepository.save(entity);
    }


}
