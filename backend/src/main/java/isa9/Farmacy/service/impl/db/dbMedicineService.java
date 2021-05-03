package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineOrder;
import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.repository.MedQuantityRepository;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.repository.OrderRepository;
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
    private final OrderRepository orderRepository;
    private final MedQuantityRepository medQuantityRepository;

    @Autowired
    public dbMedicineService(MedicineRepository medicineRepository, OrderRepository orderRepository, MedQuantityRepository medQuantityRepository) {
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
        this.medQuantityRepository = medQuantityRepository;
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
    public Medicine save(Medicine entity) { return this.medicineRepository.save(entity); }

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
