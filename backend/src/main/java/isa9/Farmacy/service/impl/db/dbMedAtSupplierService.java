package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.MedicineAtSupplier;
import isa9.Farmacy.model.Supplier;
import isa9.Farmacy.repository.MedAtSupplierRepository;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.service.MedicineAtSupplierService;
import isa9.Farmacy.service.impl.base.MedicineAtSupplierServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbMedAtSupplierService extends MedicineAtSupplierServiceBase implements MedicineAtSupplierService {
    private final MedAtSupplierRepository medAtSupplierRepository;
    private final MedicineRepository medicineRepository;

    @Autowired
    public dbMedAtSupplierService(MedAtSupplierRepository medAtSupplierRepository, MedicineRepository medicineRepository){
        this.medAtSupplierRepository = medAtSupplierRepository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public List<MedicineAtSupplier> findAll() {
        return this.medAtSupplierRepository.findAll();
    }

    @Override
    public MedicineAtSupplier findOne(Long id) {
        return this.medAtSupplierRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineAtSupplier save(MedicineAtSupplier entity) {
        return this.medAtSupplierRepository.save(entity);
    }

    @Override
    public void delete(MedicineAtSupplier entity) {
        Supplier supplier = entity.getSupplier();
        for(MedicineAtSupplier mas : supplier.getMedicinesInStock()){
            if(mas.getQuantity().getMedicine().equals(entity.getQuantity().getMedicine())){
                supplier.getMedicinesInStock().remove(mas);
                break;
            }
        }
        this.userService.save(supplier);

        this.medAtSupplierRepository.delete(entity);
    }
}
