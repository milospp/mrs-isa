package isa9.Farmacy.controller;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.PatientRegistrationDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService){
        this.pharmacyService = pharmacyService;
    }


    @GetMapping("all-pharmacies")
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> resultDTOS = new ArrayList<>();
        for (Pharmacy p : this.pharmacyService.findAll()){
            resultDTOS.add(new PharmacyDTO(p.getName(), p.getDescription(),
                    p.getId(), p.getAddress()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("pharmacy-{id}")
    public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable Long id){
        Pharmacy pharmacy = pharmacyService.findOne(id);

        PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getName(), pharmacy.getDescription(), pharmacy.getId(), pharmacy.getAddress());
        return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);

    }


    @PostMapping("register/pharmacy")
    public ResponseEntity<Boolean> registerPharmacy(@RequestBody PharmacyDTO pDTO) {
        Pharmacy newlyRegistered = new Pharmacy(pDTO.getName(), pDTO.getAddress(),
                pDTO.getDescription(), pDTO.getId());
        if(pharmacyService.pharmacyExists(newlyRegistered)) return new ResponseEntity<>(false, HttpStatus.OK);
        pharmacyService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<> (true, HttpStatus.OK);

    }
}
