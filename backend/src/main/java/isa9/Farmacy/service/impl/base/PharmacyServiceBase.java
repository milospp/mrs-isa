package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.service.PharmacyService;

public abstract class PharmacyServiceBase implements PharmacyService {


    @Override
    public boolean pharmacyExists(Pharmacy p) {
        for(Pharmacy ph : this.findAll()){
            if(ph.getName().equals(p.getName()) && ph.getAddress().equals(p.getAddress())) return true;
        };

        return false;
    }
}