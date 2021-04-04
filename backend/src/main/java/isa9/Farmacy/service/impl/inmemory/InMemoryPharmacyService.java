package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import org.springframework.stereotype.Component;
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

        // potrebno za prikaz lekovaaa
        Pharmacy apoteka = new Pharmacy("PrimerApoteke", new Address("ulica", "broj", "grad", "drzava"), "opis", 5L);
        apoteka.getAllMedicines().add(new MedicineQuantity(new Medicine("Kod1", "Ime1", "Strkt1", "Man1", "Note1", 1, "Oblik1", "Tip1", null, null), 2));
        apoteka.getAllMedicines().add(new MedicineQuantity(new Medicine("Kod2", "Ime2", "Strk2", "Man2", "Note2", 2, "Oblik2", "Tip2", null, null), 7));
        //string code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, Set<Medicine> replacementMedication
        pharmacies.put(5L, apoteka);
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
