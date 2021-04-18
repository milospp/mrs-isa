package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.DoctorRepository;
import isa9.Farmacy.repository.PharmacyRepository;
import isa9.Farmacy.repository.WorkRepository;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
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
    private final WorkRepository workRepository;

    @Autowired
    public dbPharmacyService(PharmacyRepository pharmacyRepository, WorkRepository workRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.workRepository = workRepository;
    }

    @Override
    public List<Pharmacy> findAll() {
        return this.pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy findOne(Long id) {
        return this.pharmacyRepository.findById(id).orElse(null);
    }

    @Override
    public Pharmacy save(Pharmacy entity) {
        return this.pharmacyRepository.save(entity);
    }


    @Override
    public List<Work> findDoctorsWork(Doctor doctor) {
        return workRepository.findAllByDoctor(doctor);
    }

    @Override
    public boolean pharmacyExists(Pharmacy p) {
        List<Pharmacy> allPharmacies = this.findAll();

        for(Pharmacy ph : allPharmacies){
            if(p.equals(ph)) return true;
        }

        return false;
    }
}
