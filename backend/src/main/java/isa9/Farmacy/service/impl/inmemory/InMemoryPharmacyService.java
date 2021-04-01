package isa9.Farmacy.service.impl.inmemory;

import org.springframework.stereotype.Component;
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
@Component
public class InMemoryPharmacyService extends PharmacyServiceBase implements PharmacyService {
    private final Map<Long, Pharmacy> pharmacies = new HashMap<>();

    InMemoryPharmacyService() {
        pharmacies.put(1L, new Pharmacy("Prima", new Address("Nemanjina", "2", "Belgrade", "Serbia"),
                "Otvoreni non-stop", 1L));
        pharmacies.put(2L, new Pharmacy("Apoteka Janković", new Address("Bulevar oslobođenja", "128", "Novi Sad", "Serbia"),
                "", 2L));
        pharmacies.put(3L, new Pharmacy("Benu", new Address("Bulevar oslobođenja", "43","Novi Sad", "Serbia"),
                "Radno vreme 8-18h", 3L));
        pharmacies.put(4L, new Pharmacy("Laurus", new Address("Trg slobode", "8", "Novi Sad", "Serbia"),
                "Nedeljom ne radimo", 4L));
        pharmacies.put(5L, new Pharmacy("PrimerApoteke", new Address("ulica", "broj", "grad", "drzava"),
                "opis", 5L));


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
        long id = this.pharmacies.size() + 1;
        entity.setId(id);
        this.pharmacies.put(id, entity);
        return entity;
    }

    @Override
    public boolean pharmacyExists(Pharmacy p) {
        for(Pharmacy ph : this.pharmacies.values()){
            if(ph.getName().equals(p.getName()) && ph.getAddress().equals(p.getAddress())) return true;
        };

        return false;
    }

}
