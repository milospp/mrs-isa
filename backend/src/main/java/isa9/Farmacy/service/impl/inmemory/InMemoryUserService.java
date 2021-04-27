
//package isa9.Farmacy.service.impl.inmemory;
//
//import isa9.Farmacy.model.*;
//import isa9.Farmacy.service.UserService;
//import isa9.Farmacy.service.impl.base.UserServiceBase;
//import isa9.Farmacy.support.PaginationSortSearchDTO;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.*;
//
//@Service
//public class InMemoryUserService extends UserServiceBase implements UserService {
//
//    private final Map<Long, User> users = new HashMap<>();
//
//    InMemoryUserService() {
//        Medicine med1 = new Medicine(1L, "AZP2", "Phiiizer", "Some good thinkgs",
//                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, null);
//        Medicine med2= new Medicine(2L, "GSP2", "Phiiizer", "Some good thinkgs",
//                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, new HashSet<Medicine>(Arrays.asList(med1)));
//
//        Pharmacy ph1 = new Pharmacy("Prima", new Address("Nemanjina", "2", "Belgrade", "Serbia"),
//                "Otvoreni non-stop", 1L);
//
//        Pharmacy ph2 = new Pharmacy("Apoteka Janković", new Address("Bulevar oslobođenja", "128", "Novi Sad", "Serbia"),
//                "", 2L);
//
//        Penality penality1 = new Penality(1L, "User didn't apeared on appointment", LocalDate.now().minusMonths(1));
//        Penality penality2 = new Penality(2L, "User didn't apeared on appointment", LocalDate.now().minusMonths(2));
//        Penality penality3 = new Penality(3L, "User didn't apeared on appointment", LocalDate.now());
//
////        Patient p1 = new Patient(1L, "Isidora", "Stanic", "isidora@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
////        Patient p2 = new Patient(2L, "Milica", "Djumic", "milica@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
////        p1.getAllergies().add(med1);
////        p1.getAllergies().add(med2);
////        p2.getAllergies().add(med1);
////        p1.getSubscriptions().add(ph1);
////        p1.getPenalties().add(penality1);
////        p1.getPenalties().add(penality2);
////        p1.getPenalties().add(penality3);
//
//        //Adding pharmacy admins---------------------------------------------------------------------------
//        PharmacyAdmin pha1 = new PharmacyAdmin(5L, "Nikola", "Nikolic", "nikola@mail.com", "password",
//                new Address("ulica", "broj", "grad", "drzava"),"111111111", ph1);
//        PharmacyAdmin pha2 = new PharmacyAdmin(6L, "Marko", "Markovic", "marko@mail.com", "password",
//                new Address("ulica", "broj", "grad", "drzava"),"111111111", ph2);
//
//
//        users.put(pha1.getId(), pha1);
//        users.put(pha2.getId(), pha2);
//
//        //-------------------------------------------------------------------------------------------------
//
////        users.put(1L, p1);
////        users.put(2L, p2);
////        users.put(3L, new Patient(3L, "Mladen", "Vasic", "mladen@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
////        users.put(4L, new Patient(4L, "Milos", "Popovic", "milos@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
//
//        Pharmacy apoteka = new Pharmacy("PrimerApoteke", new Address("ulica", "broj", "grad", "drzava"), "opis", 5L);
//
//
//
//        Medicine lek1 = new Medicine(1L, "Kod1", "Ime1", "Strkt1", "Man1", "Note1", 1, "Oblik1", "Tip1", null, null);
//        MedPrice cena1 = new MedPrice(17L, LocalDateTime.now(), 20.5, null);
//        Medicine lek2 = new Medicine(2L, "Kod2", "Ime2", "Strk2", "Man2", "Note2", 2, "Oblik2", "Tip2", null, null);
//        MedPrice cena2 = new MedPrice(17L, LocalDateTime.now(), 20.5, null);
//
//        MedicineInPharmacy mip1 = new MedicineInPharmacy(17L, cena1, lek1, 2, apoteka);
//        MedicineInPharmacy mip2 = new MedicineInPharmacy(18L, cena2, lek2, 7, apoteka);
//        cena1.setMedicineInPharmacy(mip1);
//        cena2.setMedicineInPharmacy(mip2);
//
//
//        apoteka.getMedicines().add(mip1);
//        apoteka.getMedicines().add(mip2);
//        //string code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, Set<Medicine> replacementMedication
//
//        pha1.setPharmacy(apoteka);
//        Pharmacy apoteka2 = new Pharmacy("PrimerApoteke2", new Address("ulica2", "broj2", "grad2", "drzava2"), "opis2", 6L);
//
//        users.put(7L, new PharmacyAdmin(7L, "Admin", "Apoteke", "mejl@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789", apoteka));
//
//        Pharmacist maja = new Pharmacist(8L, "Maja", "Markovic", "maja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
//        users.put(8L, maja);
//        apoteka.hireDoctor(1L, maja, LocalTime.now(), LocalTime.now());
//
//        Pharmacist ana = new Pharmacist(9L, "ANa", "Markovic", "ana@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
//        users.put(9L, ana);
//        apoteka.hireDoctor(2L, ana, LocalTime.now(), LocalTime.now());
//
////        Dermatologist dermatolog = new Dermatologist(10L, "Sanja", "Markovic", "sanja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
////        apoteka.hireDoctor(3L, dermatolog, LocalTime.now(), LocalTime.now());
////        apoteka2.hireDoctor(4L, dermatolog, LocalTime.now(), LocalTime.now());
////        users.put(10L, dermatolog);
//
//    }
//
//    @Override
//    public List<User> findAll() {
//        return new ArrayList<>(this.users.values());
//    }
//
//    @Override
//    public User findOne(Long id) {
//        return this.users.getOrDefault(id, null);
//    }
//
//    @Override
//    public User save(User entity) {
//        long id = this.users.size() + 1;
//        entity.setId(id);
//        this.users.put(id, entity);
//        return entity;
//    }
//
//    @Override
//    public boolean isAvaibleEmail(String em) {
//        boolean povratna = true;
//        for (User u : users.values()) {
//            if (u.getEmail().equals(em)) {
//                povratna = false;
//                break;
//            }
//        }
//        return povratna;
//    }
//
//
//    @Override
//    public PharmacyAdmin findPharmacyAdmin(Long pharmacyId) {
//        PharmacyAdmin phAdmin = null;
//        for(User u : this.users.values()){
//            if(u.getRole() == UserRole.PHARMACY_ADMIN){
//                phAdmin = (PharmacyAdmin) u;
//                try{
//                    if(phAdmin.getPharmacy().getId() == pharmacyId) return phAdmin;
//                }catch(NullPointerException e){
//                    continue;
//                }
//            }
//        }
//        phAdmin = null;
//
//        return phAdmin;
//    }
//
//    @Override
//    public List<Patient> getAllMyPatientsPaged(PaginationSortSearchDTO pssDTO) {
//        return null;
//    }
//
//    @Override
//    public long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO) {
//        return 0;
//    }
//
//    @Override
//    public Doctor getDoctorById(Long id) {
//        return null;
//    }
//
//}

package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import isa9.Farmacy.support.PaginationSortSearchDTO;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class InMemoryUserService extends UserServiceBase implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    InMemoryUserService() {
//        Medicine med1 = new Medicine(1L, "AZP2", "Phiiizer", "Some good thinkgs",
//                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, null);
//        Medicine med2= new Medicine(2L, "GSP2", "Phiiizer", "Some good thinkgs",
//                "MAANAF", "Do not drive", 2, "Pills", "AntiDepresive", DispencingMedicine.WITH_RECEIPT, new HashSet<Medicine>(Arrays.asList(med1)));
//
//        Pharmacy ph1 = new Pharmacy("Prima", new Address("Nemanjina", "2", "Belgrade", "Serbia"),
//                "Otvoreni non-stop", 1L);
//
//        Pharmacy ph2 = new Pharmacy("Apoteka Janković", new Address("Bulevar oslobođenja", "128", "Novi Sad", "Serbia"),
//                "", 2L);
//
//        Penality penality1 = new Penality(1L, "User didn't apeared on appointment", LocalDate.now().minusMonths(1));
//        Penality penality2 = new Penality(2L, "User didn't apeared on appointment", LocalDate.now().minusMonths(2));
//        Penality penality3 = new Penality(3L, "User didn't apeared on appointment", LocalDate.now());
//
//        Patient p1 = new Patient(1L, "Isidora", "Stanic", "isidora@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
//        Patient p2 = new Patient(2L, "Milica", "Djumic", "milica@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789");
//        p1.getAllergies().add(med1);
//        p1.getAllergies().add(med2);
//        p2.getAllergies().add(med1);
//        p1.getSubscriptions().add(ph1);
//        p1.getPenalties().add(penality1);
//        p1.getPenalties().add(penality2);
//        p1.getPenalties().add(penality3);
//
//        //Adding pharmacy admins---------------------------------------------------------------------------
//        PharmacyAdmin pha1 = new PharmacyAdmin(5L, "Nikola", "Nikolic", "nikola@mail.com", "password",
//                new Address("ulica", "broj", "grad", "drzava"),"111111111", ph1);
//        PharmacyAdmin pha2 = new PharmacyAdmin(6L, "Marko", "Markovic", "marko@mail.com", "password",
//                new Address("ulica", "broj", "grad", "drzava"),"111111111", ph2);
//
//
//        users.put(pha1.getId(), pha1);
//        users.put(pha2.getId(), pha2);
//
//        //-------------------------------------------------------------------------------------------------
//
//        users.put(1L, p1);
//        users.put(2L, p2);
//        users.put(3L, new Patient(3L, "Mladen", "Vasic", "mladen@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
//        users.put(4L, new Patient(4L, "Milos", "Popovic", "milos@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789"));
//
//        Pharmacy apoteka = new Pharmacy("PrimerApoteke", new Address("ulica", "broj", "grad", "drzava"), "opis", 5L);
//
//
//
//        Medicine lek1 = new Medicine(1L, "Kod1", "Ime1", "Strkt1", "Man1", "Note1", 1, "Oblik1", "Tip1", null, null);
//        MedPrice cena1 = new MedPrice(17L, LocalDateTime.now(), 20.5, null);
//        Medicine lek2 = new Medicine(2L, "Kod2", "Ime2", "Strk2", "Man2", "Note2", 2, "Oblik2", "Tip2", null, null);
//        MedPrice cena2 = new MedPrice(17L, LocalDateTime.now(), 20.5, null);
//
//        MedicineInPharmacy mip1 = new MedicineInPharmacy(17L, cena1, lek1, 2, apoteka);
//        MedicineInPharmacy mip2 = new MedicineInPharmacy(18L, cena2, lek2, 7, apoteka);
//        cena1.setMedicineInPharmacy(mip1);
//        cena2.setMedicineInPharmacy(mip2);
//
//
//        apoteka.getMedicines().add(mip1);
//        apoteka.getMedicines().add(mip2);
//        //string code, String name, String structure, String manufacturer, String note, int points, String shape, String type, DispencingMedicine perscription, Set<Medicine> replacementMedication
//
//        pha1.setPharmacy(apoteka);
//        Pharmacy apoteka2 = new Pharmacy("PrimerApoteke2", new Address("ulica2", "broj2", "grad2", "drzava2"), "opis2", 6L);
//
//        users.put(7L, new PharmacyAdmin(7L, "Admin", "Apoteke", "mejl@mail.com", "1234", new Address("ulica", "broj", "grad", "drzava"), "123-456-789", apoteka));
//
//        Pharmacist maja = new Pharmacist(8L, "Maja", "Markovic", "maja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
//        users.put(8L, maja);
//        apoteka.hireDoctor(1L, maja, LocalTime.now(), LocalTime.now());
//
//        Pharmacist ana = new Pharmacist(9L, "ANa", "Markovic", "ana@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
//        users.put(9L, ana);
//        apoteka.hireDoctor(2L, ana, LocalTime.now(), LocalTime.now());
//
//        Dermatologist dermatolog = new Dermatologist(10L, "Sanja", "Markovic", "sanja@gmail.com","majacar", new Address("ulica", "broj", "grad", "drzava"), "12345");
//        apoteka.hireDoctor(3L, dermatolog, LocalTime.now(), LocalTime.now());
//        apoteka2.hireDoctor(4L, dermatolog, LocalTime.now(), LocalTime.now());
//        users.put(10L, dermatolog);

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
        long id = this.users.size() + 1;
        entity.setId(id);
        this.users.put(id, entity);
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


    @Override
    public PharmacyAdmin findPharmacyAdmin(Long pharmacyId) {
        PharmacyAdmin phAdmin = null;
        for(User u : this.users.values()){
            if(u.getRole().getName().equals("PHARMACY_ADMIN")){
                phAdmin = (PharmacyAdmin) u;
                try{
                    if(phAdmin.getPharmacy().getId() == pharmacyId) return phAdmin;
                }catch(NullPointerException e){
                    continue;
                }
            }
        }
        phAdmin = null;

        return phAdmin;
    }

    @Override
    public List<Patient> getAllMyPatientsPaged(PaginationSortSearchDTO pssDTO) {
        return null;
    }

    @Override
    public long getAllMyPatientsTotalCount(PaginationSortSearchDTO pssDTO) {
        return 0;
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return null;
    }

    @Override
    public Patient getPatientById(Long id) {
        throw new NotYetImplementedException();
    }

}

