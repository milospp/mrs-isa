package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.Address;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import isa9.Farmacy.service.PatientService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class InMemoryPatientService implements PatientService {

    private final Map<Long, Patient> patients = new HashMap<>();

    InMemoryPatientService() {
        patients.put(1L, new Patient(1L, "isidora", "Isidora", "Stanic", "isidora@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        patients.put(2L, new Patient(2L, "milica", "Milica", "Djumic", "milica@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        patients.put(3L, new Patient(3L, "mladen", "Mladen", "Vasic", "mladen@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        patients.put(4L, new Patient(4L, "milos", "Milos", "Popovic", "milos@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(this.patients.values());
    }

    @Override
    public Patient findOne(Long id) {
        return this.patients.getOrDefault(id, null);
    }

    @Override
    public Patient save(Patient entity) {
        this.patients.put(entity.getId(), entity);
        return entity;
    }
}
