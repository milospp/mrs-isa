package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class InMemoryUserService extends UserServiceBase implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    InMemoryUserService() {
        Medicine med1 = new Medicine("AZP2", "Phiiizer", "Some good thinkgs",
                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, null);
        Medicine med2= new Medicine("GSP2", "Phiiizer", "Some good thinkgs",
                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, new HashSet<Medicine>(Arrays.asList(med1)));

        Pharmacy ph1 = new Pharmacy("Prima", new Address("Nemanjina", "2", "Belgrade", "Serbia"),
                "Otvoreni non-stop", 1L);

        Penality penality1 = new Penality(1L, "User didn't apeared on appointment", LocalDate.now().minusMonths(1));
        Penality penality2 = new Penality(2L, "User didn't apeared on appointment", LocalDate.now().minusMonths(2));
        Penality penality3 = new Penality(3L, "User didn't apeared on appointment", LocalDate.now());

        Patient p1 = new Patient(1L, "Isidora", "Stanic", "isidora@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
        Patient p2 = new Patient(2L, "Milica", "Djumic", "milica@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
        p1.getAllergies().add(med1);
        p1.getAllergies().add(med2);
        p2.getAllergies().add(med1);
        p1.getSubscriptions().add(ph1);
        p1.getPenalties().add(penality1);
        p1.getPenalties().add(penality2);
        p1.getPenalties().add(penality3);



        users.put(1L, p1);
        users.put(2L, p2);
        users.put(3L, new Patient(3L, "Mladen", "Vasic", "mladen@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
        users.put(4L, new Patient(4L, "Milos", "Popovic", "milos@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
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
