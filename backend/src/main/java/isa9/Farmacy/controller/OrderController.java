package isa9.Farmacy.controller;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.model.dto.MedicineQuantityDTO;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final MedicineService medicineService;
    private final UserService userService;
    private final MedOrderToMedOrderDTO medOrderToMedOrderDTO;
    private final PharmacyService pharmacyService;
    private final OfferDTOtoOffer offerDTOtoOffer;

    @Autowired
    public OrderController(OrderService orderService, MedicineService medicineService, UserService userService,
                           MedOrderToMedOrderDTO medOrderToMedOrderDTO, PharmacyService pharmacyService,
                            OfferDTOtoOffer offerDTOtoOffer) {
        this.orderService = orderService;
        this.medicineService = medicineService;
        this.userService = userService;
        this.medOrderToMedOrderDTO = medOrderToMedOrderDTO;
        this.pharmacyService = pharmacyService;
        this.offerDTOtoOffer = offerDTOtoOffer;
    }

    @GetMapping("/availableOrders/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<List<MedicineOrderDTO>> getAllAvailableOrders(@PathVariable Long id) {
        List<MedicineOrder> availableOrders = this.orderService.getAvailableOrders((Supplier) this.userService.findOne(id));
        return new ResponseEntity<>(medOrderToMedOrderDTO.convert(availableOrders), HttpStatus.OK);
    }

    @PostMapping("/add/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> addOrder(@PathVariable Long idAdmina, @RequestBody OrderHelp pomocna) {
        User user = userService.findOne(idAdmina);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        PharmacyAdmin admin = (PharmacyAdmin) user;
        povratna = 1;

        pomocna.setStartDate(LocalDateTime.now());
        if (pomocna.getStartDate().isAfter(pomocna.getEndDate())) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 0;

        MedQuantityDTOtoMedQuantity konverter = new MedQuantityDTOtoMedQuantity(this.medicineService);
        List<MedicineQuantity> listaLekova = konverter.convert(pomocna.getMedicines());
        this.pharmacyService.checkMedicineInPharmacy(admin.getPharmacy(), listaLekova);

        MedicineOrder porudzbina = new MedicineOrder();
        porudzbina.setPharmacy(admin.getPharmacy());
        porudzbina.setAuthor(admin);
        porudzbina.setAllMedicines(listaLekova);
        porudzbina.setStartDate(pomocna.getStartDate());
        porudzbina.setEndDate(pomocna.getEndDate());
        porudzbina.setAllOffer(new ArrayList<>());
        this.orderService.save(porudzbina);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<MedicineOrderDTO>> getOrders(@PathVariable Long idAdmina) {
        MedOrderToMedOrderDTO konverter = new MedOrderToMedOrderDTO();
        List<MedicineOrderDTO> povratna = konverter.convert(this.orderService.getAdminOrders(idAdmina));
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> deleteOrder(@RequestBody MedicineOrderDTO porudzbina) {
        int povratna = -1;
        MedOrderDTOtoMedOrder konverter = new MedOrderDTOtoMedOrder(this.orderService);
        MedicineOrder zaBrisanje = konverter.convert(porudzbina);
        if (zaBrisanje == null) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        povratna = 0;
        this.orderService.delete(zaBrisanje);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/findOne")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<MedicineOrderDTO> findOneOrder(@RequestBody MedicineOrderDTO porudzbina) {
        MedOrderDTOtoMedOrder konverterDTO = new MedOrderDTOtoMedOrder(this.orderService);
        MedicineOrder odgovarajuca = konverterDTO.convert(porudzbina);
        if (odgovarajuca == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        MedOrderToMedOrderDTO konverter = new MedOrderToMedOrderDTO();
        MedicineOrderDTO povratna = konverter.convert(odgovarajuca);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> editOrder(@RequestBody MedicineOrderDTO porudzbina) {
        int povratna = -1;
        MedOrderDTOtoMedOrder konverterDTO = new MedOrderDTOtoMedOrder(this.orderService);
        MedicineOrder odgovarajuca = konverterDTO.convert(porudzbina);
        if (odgovarajuca == null) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);

        povratna = 1;
        if (porudzbina.getEndDate().isBefore(LocalDateTime.now())) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 0;
        odgovarajuca.setEndDate(porudzbina.getEndDate());

        MedQuantityDTOtoMedQuantity konverter = new MedQuantityDTOtoMedQuantity(this.medicineService);
        List<MedicineQuantity> listaLekova = konverter.convert(porudzbina.getAllMedicines());

        odgovarajuca.setAllMedicines(listaLekova);

        this.orderService.save(odgovarajuca);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/findOrder/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<MedicineOrderDTO> findOrderById(@PathVariable Long id){
        MedicineOrder order = this.orderService.findOne(id);
        return new ResponseEntity<>(this.medOrderToMedOrderDTO.convert(order), HttpStatus.OK);
    }

    @PostMapping("/choose")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> chooseOrder(@RequestBody OfferDTO ponuda) {
        Offer ponudica = this.offerDTOtoOffer.convertOffer(ponuda);
        int povratna = this.orderService.chooseOffer(ponudica, ponuda.getOrder());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
}
