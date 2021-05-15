package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.MedicineAtSupplier;
import isa9.Farmacy.model.Supplier;
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
}
