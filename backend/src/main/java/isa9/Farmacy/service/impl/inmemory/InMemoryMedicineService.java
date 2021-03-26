package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.MedicineServiceBase;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryMedicineService extends MedicineServiceBase implements MedicineService {

    private final Map<String, Medicine> medicines = new HashMap<>();

    InMemoryMedicineService() {
        Medicine med1 = new Medicine("AZP2", "Phiiizer", "Some good thinkgs",
                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, null);
        Medicine med2= new Medicine("GSP2", "Phiiizer", "Some good thinkgs",
                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, new HashSet<Medicine>(Arrays.asList(med1)));
        medicines.put(med1.getCode(), med1);
        medicines.put(med2.getCode(), med2);
    }

    @Override
    public List<Medicine> findAll() {
        return new ArrayList<>(this.medicines.values());
    }

    @Override
    public Medicine findOne(Long id) {
        return this.medicines.getOrDefault(id, null);
    }

    @Override
    public Medicine save(Medicine entity) {
        this.medicines.put(entity.getCode(), entity);
        return entity;
    }


}
