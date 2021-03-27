package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryUserService extends UserServiceBase implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    InMemoryUserService() {
        users.put(1L, new Patient(1L, "Isidora", "Stanic", "isidora@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        users.put(2L, new Patient(2L, "Milica", "Djumic", "milica@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        users.put(3L, new Patient(3L, "Mladen", "Vasic", "mladen@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        users.put(4L, new Patient(4L, "Milos", "Popovic", "milos@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));

        Pharmacy apoteka = new Pharmacy("PrimerApoteke", new Address("ulica", "broj", "grad", "drzava"), "opis", 1L);
        users.put(5l, new PharmacyAdmin(5L, "Admin", "Apoteke", "mejl@mail.com", "1234", apoteka,  new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        users.put(6L, new Pharmacist(6L, "Maja", "Markovic", "maja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345", apoteka));
        users.put(7L, new Pharmacist(7L, "ANa", "Markovic", "ana@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345", apoteka));
        Dermatologist dermatolog = new Dermatologist(8L, "Sanja", "Markovic", "sanja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
        dermatolog.getPharmacies().add(apoteka);
        users.put(8L, dermatolog);

    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.users.values());
    }

    @Override
    public User findOne(Long id) {
        return this.users.getOrDefault(id, null);
    }

    @Override
    public User save(User entity) {
        this.users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public boolean isAvaibleEmail(String em) {
        boolean povratna = true;
        for (User u : users.values()) {
            if (u.getEmail().equals(em)) {
                povratna = false;
                break;
            }
        }
        return povratna;
    }
}
