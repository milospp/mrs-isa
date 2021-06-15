package isa9.Farmacy.controller;

import isa9.Farmacy.model.MedicineQuantity;
import isa9.Farmacy.model.Offer;
import isa9.Farmacy.model.OfferStatus;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.OfferDTO;
import isa9.Farmacy.model.dto.OfferSearchDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.PharmacySearchDTO;
import isa9.Farmacy.service.MedicineAtSupplierService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.OfferService;
import isa9.Farmacy.service.OrderService;
import isa9.Farmacy.support.OfferDTOtoOffer;
import isa9.Farmacy.support.OfferToOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final OrderService orderService;
    private final MedicineService medicineService;
    private final OfferToOfferDTO offerToOfferDTO;
    private final OfferDTOtoOffer offerDTOtoOffer;
    private final MedicineAtSupplierService medicineAtSupplierService;

    @Autowired
    public OfferController(MedicineService medicineService, OfferService offerService, OfferToOfferDTO offerToOfferDTO, OfferDTOtoOffer offerDTOtoOffer, MedicineAtSupplierService medicineAtSupplierService, OrderService orderService) {
        this.medicineService = medicineService;
        this.offerService = offerService;
        this.offerDTOtoOffer = offerDTOtoOffer;
        this.offerToOfferDTO = offerToOfferDTO;
        this.medicineAtSupplierService = medicineAtSupplierService;
        this.orderService = orderService;
    }

    @PostMapping("/newOffer")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Integer> createOffer(@RequestBody OfferDTO offerDTO){
        Offer newOffer = offerDTOtoOffer.convert(offerDTO);
        this.offerService.saveNewOffer(newOffer);
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @GetMapping("/offersOfSupplier/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<List<OfferDTO>> getAllOffers(@PathVariable Long id){
        List<Offer> offersOfSupplier = this.offerService.getOffersOfSupplier(id);
        return new ResponseEntity<>(this.offerToOfferDTO.convert(offersOfSupplier), HttpStatus.OK);
    }

    @PostMapping("search")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<List<OfferDTO>> SearchPharmacies(@RequestBody(required=false) OfferSearchDTO offerSearchDTO) {
        List<Offer> offers = this.offerService.findAll();
        offers = offerService.filterOffers(offers, offerSearchDTO);
        List<OfferDTO> resultDTOS = offerToOfferDTO.convert(offers);
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    @PostMapping("/updateOffer")
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public ResponseEntity<Integer> updateOffer(@RequestBody OfferDTO offerDTO){
        Offer toBeUpdated = this.offerDTOtoOffer.convert(offerDTO);
        this.offerService.updateOffer(toBeUpdated);
        return new ResponseEntity<>(0, HttpStatus.OK);
    }
}
