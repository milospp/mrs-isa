package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineAtSupplier;
import isa9.Farmacy.model.Supplier;
import isa9.Farmacy.model.SupplierMedPrice;
import isa9.Farmacy.service.MedicineAtSupplierService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public abstract class MedicineAtSupplierServiceBase implements MedicineAtSupplierService {
    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Override
    public Set<MedicineAtSupplier> medicinesOfSupplier(long id){
        Supplier supplier = (Supplier) this.userService.findOne(id);
        return supplier.getMedicinesInStock();
    }

    @Override
    public void updateMedicineAtSupplier(MedicineAtSupplier mas, double newPrice, int newQuantity) {
        mas.getQuantity().setQuantity(newQuantity);
        mas.getSupplierPrice().setPrice(newPrice);

        Supplier supplier = mas.getSupplier();
        for(MedicineAtSupplier m : supplier.getMedicinesInStock()){
            if(m.getQuantity().getMedicine().getCode().equals(mas.getQuantity().getMedicine().getCode())){
                m = mas;
                break;
            }
        }

        this.save(mas);
    }

    @Override
    public void addMedicineAtSupplier(MedicineAtSupplier mas) {
        Supplier supplier = mas.getSupplier();
        supplier.getMedicinesInStock().add(mas);
        mas.getSupplierPrice().getMedicineAtSupplier().setId(mas.getId());
        this.save(mas);

    }
}
