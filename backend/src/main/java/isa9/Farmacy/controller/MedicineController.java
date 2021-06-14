package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.service.*;
import isa9.Farmacy.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;
    private final MedQuantityService medQuantityService;
    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final RatingService ratingService;
    private final MedReservationService medReservationService;
    private final MedInPharmaService medInPharmaService;
    private final MedReservationToMedReservationDTO medReservationToMedReservationDTO;
    private final MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;
    private final MedicineToMedicineDTO medicineToMedicineDTO;
    private final RatingToRatingDTO ratingToRatingDTO;
    private final MedicineQuantityToMedicineQuantityDTO medicineQuantityToMedicineQuantityDTO;
    private final MedicineAtSupplierService medicineAtSupplierService;
    private final MedicineAtSupplierToMedAtSupplierDTO medicineAtSupplierToMedAtSupplierDTO;
    private final MedAtSupplierDTOtoMedAtSupplier medAtSupplierDTOtoMedAtSupplier;

    @Autowired
    public MedicineController(MedicineService medicineService, UserService userService, PharmacyService pharmacyService, RatingService ratingService, MedReservationService medReservationService, MedInPharmaService medInPharmaService, MedReservationToMedReservationDTO medReservationToMedReservationDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO, MedicineToMedicineDTO medicineToMedicineDTO, RatingToRatingDTO ratingToRatingDTO, MedQuantityService medQuantityService, MedicineQuantityToMedicineQuantityDTO medicineQuantityToMedicineQuantityDTO, MedicineAtSupplierService medicineAtSupplierService, MedicineAtSupplierToMedAtSupplierDTO medicineAtSupplierToMedAtSupplierDTO, MedAtSupplierDTOtoMedAtSupplier medAtSupplierDTOtoMedAtSupplier) {
        this.medicineService = medicineService;
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.ratingService = ratingService;
        this.medReservationService = medReservationService;
        this.medInPharmaService = medInPharmaService;
        this.medReservationToMedReservationDTO = medReservationToMedReservationDTO;
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
        this.medicineToMedicineDTO = medicineToMedicineDTO;
        this.ratingToRatingDTO = ratingToRatingDTO;
        this.medQuantityService = medQuantityService;
        this.medicineQuantityToMedicineQuantityDTO = medicineQuantityToMedicineQuantityDTO;
        this.medicineAtSupplierService = medicineAtSupplierService;
        this.medicineAtSupplierToMedAtSupplierDTO = medicineAtSupplierToMedAtSupplierDTO;
        this.medAtSupplierDTOtoMedAtSupplier = medAtSupplierDTOtoMedAtSupplier;
    }

    @GetMapping("tmp-test")
    public ResponseEntity<Boolean> debug(){
        medReservationService.checkForExpiredReservations();
        this.medInPharmaService.checkForExpiredActionOrPromotion();
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @PostMapping("{medId}/pharmacy/{pharmacyId}/reserve/{doctorId}")
    public ResponseEntity<MedReservationDTO> reserveMedicine(@PathVariable Long medId, @PathVariable Long pharmacyId,
                 @PathVariable Long doctorId, @RequestBody MedReservationFormDTO form){
        // TODO: Get patient from session
        User user = userService.getLoggedInUser();

        if (!form.getPatientId().equals(user.getId()) && !user.getRole().getName().equals("DERMATOLOGIST") && !user.getRole().getName().equals("PHARMACIST")){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        form.setMedicineId(medId);
        form.setPharmacyId(pharmacyId);

        MedReservation medReservation = medReservationService.reserveMedicine(form, doctorId);

        if (medReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        MedReservation reservationWithId = medReservationService.getByCode(medReservation.getCode());

        MedReservationDTO dto = medReservationToMedReservationDTO.convert(reservationWithId);
        System.out.println("Id rezervacije: " + dto.getId());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("{medId}/pharmacy/{pharmacyId}/reserve")
    public ResponseEntity<MedReservationDTO> reserveMedicineAsPatient(@PathVariable Long medId, @PathVariable Long pharmacyId,
                                                             @RequestBody MedReservationFormDTO form){
        // TODO: Get patient from session
        User user = userService.getLoggedInUser();

        if (!form.getPatientId().equals(user.getId()) && !user.getRole().getName().equals("DERMATOLOGIST") && !user.getRole().getName().equals("PHARMACIST")){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        form.setMedicineId(medId);
        form.setPharmacyId(pharmacyId);
        MedReservation medReservation = medReservationService.reserveMedicine(form);

        if (medReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        MedReservation reservationWithId = medReservationService.getByCode(medReservation.getCode());

        MedReservationDTO dto = medReservationToMedReservationDTO.convert(reservationWithId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<MedicineDTO>> getMedicines(){

        List<MedicineDTO> resultDTOS = medicineToMedicineDTO.convert(this.medicineService.findAll());

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    @PostMapping("search")
    public ResponseEntity<List<MedicineDTO>> SearchMedicines(@RequestBody(required=false) MedicineSearchDTO medicineSearchDTO) {
        List<Medicine> medicines = this.medicineService.findAll();
        medicines = medicineService.filterMedicines(medicines, medicineSearchDTO);
        List<MedicineDTO> resultDTOS = medicineToMedicineDTO.convert(medicines);
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    @GetMapping("/pharmacyAdmin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<MedInPharmaDTO>> getAllMedicinePharmacyAdmin(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Set<MedicineInPharmacy> sviLekovi = ((PharmacyAdmin) user).getPharmacy().getMedicines();
        List<MedInPharmaDTO> povratna = medicineInPharmacyToMedInPharmaDTO.convert(sviLekovi);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pricelist/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<PricelistDTO> getPricelistPharmacyAdmin(@PathVariable Long id) {
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        LocalDateTime datum = LocalDateTime.now();
        List<MedInPharmaDTO> listaLekova = new ArrayList<>();
        int broj = 0;
        for (MedicineInPharmacy mp : admin.getPharmacy().getMedicines()) {
            listaLekova.add(medicineInPharmacyToMedInPharmaDTO.convert(mp));
            if (broj == 0) {
                broj++;
                datum = mp.getCurrentPrice().getStartDate(); }
            else {
                if (datum.isBefore(mp.getCurrentPrice().getStartDate()))
                    datum = mp.getCurrentPrice().getStartDate(); }
        }
        PricelistDTO povratna = new PricelistDTO(datum , listaLekova);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/edit/pharmacyAdmin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> editMedicinePharmacyAdmin(@PathVariable Long id, @RequestBody MedInPharmaDTO lek) {
        User user = userService.findOne(id);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        Set<MedicineInPharmacy> sviLekovi = ((PharmacyAdmin) user).getPharmacy().getMedicines();
        povratna = 0;
        for (MedicineInPharmacy med : sviLekovi) {
            if (med.getMedicine().getCode().equals(lek.getMedicine().getCode())) {
                med.getMedicine().setName(lek.getMedicine().getName());
                med.getMedicine().setNote(lek.getMedicine().getNote());
                med.setInStock(lek.getInStock());
                med.getMedicine().setPerscription(lek.getMedicine().getPerscription());
                med.getMedicine().setManufacturer(lek.getMedicine().getManufacturer());
                med.getMedicine().setShape(lek.getMedicine().getShape());
                med.getMedicine().setType(lek.getMedicine().getType());
                med.getMedicine().setPoints(lek.getMedicine().getPoints());
                if (med.getCurrentPrice().getPriceType() == PriceType.NORMAL) {
                    MedPrice novacena = new MedPrice();
                    novacena.setPriceType(PriceType.NORMAL);
                    novacena.setPrice(lek.getCurrentPrice());
                    novacena.setStartDate(LocalDateTime.now());
                    novacena.setMedicineInPharmacy(med);
                    med.setCurrentPrice(novacena);
                }
                medicineService.save(med.getMedicine());
                povratna = 1;
                pharmacyService.save(((PharmacyAdmin) user).getPharmacy());
                break;
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/delete/pharmacyAdmin/{id}/{code}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> deleteMedicinePharmacyAdmin(@PathVariable Long id, @PathVariable String code) {
        User user = userService.findOne(id);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        Set<MedicineInPharmacy> sviLekovi = ((PharmacyAdmin) user).getPharmacy().getMedicines();
        povratna = 0;
        MedicineInPharmacy odabrani = null;
        for (MedicineInPharmacy med : sviLekovi) {
            if (med.getMedicine().getCode().equals(code)) {
                odabrani = med;
                break;
            }
        }
        if (odabrani == null) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;
        sviLekovi.remove(odabrani);
        pharmacyService.save(((PharmacyAdmin) user).getPharmacy());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/add/pharmacyAdmin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> addMedicinePharmacyAdmin(@PathVariable Long id, @RequestBody MedInPharmaDTO lek) {
        User user = userService.findOne(id);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        Pharmacy apoteka = ((PharmacyAdmin) user).getPharmacy();
        List<Medicine> sviLekovi = medicineService.findAll();
        povratna = 0;
        for (Medicine med : sviLekovi)
            if (med.getCode().equals(lek.getMedicine().getCode())) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;

        // novi lek
        Medicine novi = new Medicine();
        novi.setCode(lek.getMedicine().getCode());
        novi.setName(lek.getMedicine().getName());
        novi.setSpecification(lek.getMedicine().getSpecification());
        novi.setType(lek.getMedicine().getType());
        novi.setShape(lek.getMedicine().getShape());
        novi.setManufacturer(lek.getMedicine().getName());
        novi.setPoints(lek.getMedicine().getPoints());
        novi.setRating(0);
        novi.setPerscription(lek.getMedicine().getPerscription());
        novi.setNote(lek.getMedicine().getNote());
        medicineService.save(novi);

        // za zamenske lekove
        Set<Medicine> zamenski = new HashSet<>();
        for (String kod : lek.getMedicine().getReplacementMedicationIds()) {
            for (Medicine m : medicineService.findAll()) {
                if (m.getCode().equals(kod)) {
                    zamenski.add(m);
                    break;
                }}}
        novi.setReplacementMedication(zamenski);
        medicineService.save(novi);

        // lek u apoteci
        MedicineInPharmacy noviUApoteci = new MedicineInPharmacy();
        noviUApoteci.setMedicine(novi);
        noviUApoteci.setPharmacy(apoteka);
        //cena leka
        MedPrice novacena = new MedPrice();
        novacena.setPriceType(PriceType.NORMAL);
        novacena.setPrice(lek.getCurrentPrice());
        novacena.setStartDate(LocalDateTime.now());
        novacena.setMedicineInPharmacy(noviUApoteci);
        noviUApoteci.setCurrentPrice(novacena);
        noviUApoteci.setInStock(lek.getInStock());
        apoteka.getMedicines().add(noviUApoteci);
        pharmacyService.save(apoteka);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }


    @PostMapping("/makeActionPromotion/{idAdmina}/{akcijaJe}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> makeActionPromotion(@PathVariable Long idAdmina, @PathVariable Boolean akcijaJe, @RequestBody MedInPharmaDTO lek) {
        int povratna = -2;
        if (lek.getEndDate().isBefore(LocalDateTime.now())) return new ResponseEntity<>(povratna, HttpStatus.OK);
        User user = userService.findOne(idAdmina);
        povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        Pharmacy apoteka = ((PharmacyAdmin) user).getPharmacy();
        povratna = 0;
        MedicineInPharmacy odabraniLek = null;
        for (MedicineInPharmacy med : apoteka.getMedicines())
            if (med.getMedicine().getCode().equals(lek.getMedicine().getCode())) {odabraniLek = med; break;}
        if (odabraniLek == null) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;
        if (odabraniLek.getCurrentPrice().getPriceType() != PriceType.NORMAL) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 2;
        //cena leka
        MedPrice novacena = new MedPrice();
        if (akcijaJe) novacena.setPriceType(PriceType.ACTION);
        else novacena.setPriceType(PriceType.PROMOTION);
        novacena.setPrice(lek.getCurrentPrice());
        novacena.setOldPrice(lek.getOldPrice());
        novacena.setStartDate(LocalDateTime.now());
        novacena.setEndDate(lek.getEndDate());
        novacena.setMedicineInPharmacy(odabraniLek);
        odabraniLek.setCurrentPrice(novacena);
        odabraniLek.setInStock(lek.getInStock());
        pharmacyService.save(apoteka);
        this.pharmacyService.sendActionMail(novacena, false);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/deleteActionPromotion/{idAdmina}/{kodLeka}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> deleteActionPromotion(@PathVariable Long idAdmina, @PathVariable String kodLeka) {
        User user = userService.findOne(idAdmina);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        Pharmacy apoteka = ((PharmacyAdmin) user).getPharmacy();
        povratna = 0;
        MedicineInPharmacy odabraniLek = null;
        for (MedicineInPharmacy med : apoteka.getMedicines())
            if (med.getMedicine().getCode().equals(kodLeka)) {odabraniLek = med; break;}
        if (odabraniLek == null) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;
        MedPrice trenutnaCena = odabraniLek.getCurrentPrice();
        if (trenutnaCena.getPriceType() == PriceType.NORMAL) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 2;
        this.pharmacyService.sendActionMail(trenutnaCena, true);
        // vracam cenu leka
        MedPrice novacena = new MedPrice();
        novacena.setPriceType(PriceType.NORMAL);
        novacena.setPrice(trenutnaCena.getOldPrice());
        novacena.setStartDate(LocalDateTime.now());
        odabraniLek.setCurrentPrice(novacena);
        novacena.setMedicineInPharmacy(odabraniLek);
        pharmacyService.save(apoteka);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<List<MedInPharmaDTO>> getMedicinePharmacy(@PathVariable Long id) {
        Pharmacy apoteka = pharmacyService.findOne(id);
        List<MedInPharmaDTO> povratna = new ArrayList<>();
        for (MedicineInPharmacy mp : apoteka.getMedicines()) {     // ne vracamo one kojih ima 0
            if (mp.getInStock() > 0) povratna.add( medicineInPharmacyToMedInPharmaDTO.convert(mp));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
    @GetMapping("/all/pharmacy/{id}")
    public ResponseEntity<List<MedInPharmaDTO>> getAllMedicinePharmacy(@PathVariable Long id) {
        Pharmacy apoteka = pharmacyService.findOne(id);
        List<MedInPharmaDTO> povratna = medicineInPharmacyToMedInPharmaDTO.convert(apoteka.getMedicines());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/{medicineId}/pharmacies")
    public ResponseEntity<Collection<MedInPharmaDTO>> getAllMedicinePharmacies(@PathVariable Long medicineId) {
        Collection<MedicineInPharmacy> medicineInPharmacies = medInPharmaService.findAllMedicinesInPharmacy(medicineId);
        return new ResponseEntity<>(medicineInPharmacyToMedInPharmaDTO.convert(medicineInPharmacies), HttpStatus.OK);
    }

    @PostMapping("/newMedicine")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Integer> addMedicine(@RequestBody MedicineDTO medicineDTO){
        List<Long> replacementMedsIds = medicineDTO.getReplacementMedicationIds().stream().map(Long::parseLong).collect(Collectors.toList());
        Medicine newMedicine = new Medicine(0L, medicineDTO.getCode(), medicineDTO.getName(), medicineDTO.getSpecification(),
                medicineDTO.getManufacturer(), medicineDTO.getNote(), medicineDTO.getPoints(), medicineDTO.getShape(),
                medicineDTO.getType(), medicineDTO.getPerscription(), medicineService.idsToMedicines(replacementMedsIds));
        boolean checkCode = this.medicineService.isCodeAvailable(newMedicine.getCode());
        if(checkCode){
            this.medicineService.save(newMedicine);
            return new ResponseEntity<>(0, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(1, HttpStatus.OK); // 0 - sve uredu, 1 - sifra leka je zauzeta
        }
    }

    @GetMapping("/resevation/{code}")
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public  ResponseEntity<MedReservationDTO> getReservationByCode(@PathVariable String code){
        System.out.println(code);
        MedReservation reservation = medReservationService.getByCode(code);

        if (reservation != null && reservation.getStatus() == MedReservationStatus.PENDING){
            MedReservationDTO reservationDTO = medReservationToMedReservationDTO.convert(reservation);
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reservation/dispense/{code}")
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public  ResponseEntity<Boolean> dispenseReservationByCode(@PathVariable String code){
        System.out.println(code);
        MedReservation reservation = medReservationService.getByCode(code);

        if (reservation != null && reservation.getStatus() == MedReservationStatus.PENDING){
            medReservationService.dispenseMedicine(reservation);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}/rating")
    public ResponseEntity<MedicineDTO> getMedicineRating(@PathVariable Long id){
        Medicine medicine = medicineService.findOne(id);
//        double rating = ratingService.getMedicineAverage(id);
//
        MedicineDTO dto = medicineToMedicineDTO.convert(medicine);
//        dto.setRating(rating);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("{medId}/rating/user/{userId}")
    public ResponseEntity<RatingDTO> getUserRatingValue(@PathVariable Long medId, @PathVariable Long userId){
        Rating rating = ratingService.getPatientMedicineRate(userId, medId);

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("{id}/rating")
    public ResponseEntity<RatingDTO> rateMedicine(@PathVariable Long id, @RequestBody RatingDTO ratingDTO){
        Rating rating = ratingService.rateMedicine(id, ratingDTO.getUser(), ratingDTO.getRating());

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<List<PriceInPharmaciesDTO>> getPricesOfMedicine(@PathVariable Long id){
        Medicine medToLookFor = this.medicineService.findOne(id);
        List<PriceInPharmaciesDTO> prices = this.medicineService.getPricesOfMedicine(medToLookFor);
        return new ResponseEntity<List<PriceInPharmaciesDTO>>(prices, HttpStatus.OK);
    }

    @GetMapping("/medicinesInStock")
    public ResponseEntity<List<MedicineDTO>> getMedicinesInStock(){
        List<MedicineDTO> inStock = new ArrayList<MedicineDTO>();
        List<Medicine> allMeds = this.medicineService.findAll();

        for(Medicine m : allMeds){
            if(this.medicineService.getPricesOfMedicine(m).isEmpty()) continue;

            inStock.add(medicineToMedicineDTO.convert(m));
        }

        return new ResponseEntity<List<MedicineDTO>>(inStock, HttpStatus.OK);
    }

    @GetMapping("/suppliersMedicines/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Map<String, MedAtSupplierDTO>> getSuppliersMedicines(@PathVariable Long id){
        Set<MedicineAtSupplier> medicineAtSupplierSet = this.medicineAtSupplierService.medicinesOfSupplier(id);
        return new ResponseEntity<>(this.medicineAtSupplierToMedAtSupplierDTO.setToMap(medicineAtSupplierSet), HttpStatus.OK);
    }

    @PostMapping("/updateSuppliersMedicine")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Boolean> updateMedicineAtSupplier(@RequestBody MedAtSupplierDTO medAtSupplierDTO){
        MedicineAtSupplier toBeUpdated = this.medicineAtSupplierService.findOne(medAtSupplierDTO.getId());
        this.medicineAtSupplierService.updateMedicineAtSupplier(toBeUpdated, medAtSupplierDTO.getCurrentPrice(), medAtSupplierDTO.getInStock());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/removeSuppliersMedicine")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Boolean> removeMedicineAtSupplier(@RequestBody MedAtSupplierDTO medAtSupplierDTO){
        MedicineAtSupplier toBeRemoved = this.medicineAtSupplierService.findOne(medAtSupplierDTO.getId());
        this.medicineAtSupplierService.delete(toBeRemoved);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/addSuppliersMedicine/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Boolean> addMedicineToSupplier(@RequestBody MedAtSupplierDTO medAtSupplierDTO, @PathVariable Long id){
        Supplier supplier = (Supplier) this.userService.findOne(id);
        MedicineAtSupplier toBeAdded = this.medAtSupplierDTOtoMedAtSupplier.convert(medAtSupplierDTO);
        SupplierMedPrice price = toBeAdded.getSupplierPrice();
        toBeAdded.setSupplierPrice(null);
        toBeAdded.setSupplier(supplier);
        toBeAdded = this.medicineAtSupplierService.save(toBeAdded);

        price.setMedicineAtSupplier(toBeAdded);
        toBeAdded.setSupplierPrice(price);
        this.medicineAtSupplierService.addMedicineAtSupplier(toBeAdded);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/patientsPurchases/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<MedReservationDTO>> getPurchasedMedicines(@PathVariable Long id){
        List<MedReservation> purchases = this.medReservationService.getPatientsPurchases((Patient) this.userService.findOne(id));

        return new ResponseEntity<>(this.medReservationToMedReservationDTO.convert(purchases), HttpStatus.OK);
    }
}
