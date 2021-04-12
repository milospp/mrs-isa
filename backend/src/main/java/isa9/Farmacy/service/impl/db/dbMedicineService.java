package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.impl.base.MedicineServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
@Service
public class dbMedicineService extends MedicineServiceBase implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Autowired
    public dbMedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public List<Medicine> findAll() {
        return this.medicineRepository.findAll();
    }

    @Override
    public Medicine findOne(Long id) {
        return this.medicineRepository.findById(id).orElse(null);
    }

    @Override
    public Medicine save(Medicine entity) {
        long nrOfAllMedicines = this.medicineRepository.findAll().size();
        entity.setId(nrOfAllMedicines + 1);

        while(this.findOne(entity.getId()) != null){
            entity.setId(entity.getId() + 1);
        }

        return this.medicineRepository.save(entity);
    }

    @Override
    public Boolean isCodeAvailable(String medicineId) {
        List<Medicine> allMeds = this.medicineRepository.findAll();
        for(Medicine med : allMeds){
            if(med.getCode().equals(medicineId)) return false;
        }
        return true;
    }

    @Override
    public Set<Medicine> idsToMedicines(List<Long> ids) {
        HashSet<Medicine> medicines = new HashSet<Medicine>();

        for(Long id : ids){
            medicines.add(this.findOne(id));
        }


        return medicines;
    }
}
