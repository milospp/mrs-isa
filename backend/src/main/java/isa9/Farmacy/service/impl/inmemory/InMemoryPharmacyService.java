package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.User;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryPharmacyService extends PharmacyServiceBase implements PharmacyService {

    private final Map<Long, Pharmacy> pharmacies = new HashMap<>();

    InMemoryPharmacyService() {
        Pharmacy pharmacy1 = new Pharmacy("Name1", new Address("1", "2", "3", "3"), "Description about", 1L);
        Pharmacy pharmacy2 = new Pharmacy("Name2", new Address("1", "2", "3", "3"), "Description about", 2L);
        pharmacies.put(1L, pharmacy1);
        pharmacies.put(1L, pharmacy2);
    }

    @Override
    public List<Pharmacy> findAll() {
        return new ArrayList<>(this.pharmacies.values());
    }

    @Override
    public Pharmacy findOne(Long id) {
        return this.pharmacies.getOrDefault(id, null);
    }

    @Override
    public Pharmacy save(Pharmacy entity) {
        this.pharmacies.put(entity.getId(), entity);
        return entity;
    }

}
