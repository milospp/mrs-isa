package isa9.Farmacy.controller;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedicineOrderDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.MedOrderToMedOrderDTO;
import isa9.Farmacy.support.MedQuantityDTOtoMedQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final MedicineService medicineService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, MedicineService medicineService, UserService userService) {
        this.orderService = orderService;
        this.medicineService = medicineService;
        this.userService = userService;
    }


    @PostMapping("/add/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> addOrderAdmin(@PathVariable Long idAdmina, @RequestBody OrderHelp pomocna) {
        User user = userService.findOne(idAdmina);
        int povratna = -1;
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        PharmacyAdmin admin = (PharmacyAdmin) user;
        povratna = 0;

        MedQuantityDTOtoMedQuantity konverter = new MedQuantityDTOtoMedQuantity(this.medicineService);
        List<MedicineQuantity> listaLekova = konverter.convert(pomocna.getMedicines());

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
    public ResponseEntity<List<MedicineOrderDTO>> getOrderAdmin(@PathVariable Long idAdmina) {
        MedOrderToMedOrderDTO konverter = new MedOrderToMedOrderDTO();
        List<MedicineOrderDTO> povratna = konverter.convert(this.orderService.getAdminOrders(idAdmina));
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }
}
