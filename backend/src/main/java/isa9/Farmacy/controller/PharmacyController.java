package isa9.Farmacy.controller;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.PatientRegistrationDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.UserDTO;
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


    @GetMapping("")
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> resultDTOS = new ArrayList<>();
        for (Pharmacy p : this.pharmacyService.findAll()){
            resultDTOS.add(new PharmacyDTO(p.getName(), p.getDescription(),
                    p.getId(), p.getAddress()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

  
    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable Long id) {
        Pharmacy pharmacy = pharmacyService.findOne(id);

        if (pharmacy == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        PharmacyDTO dto = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(),
                pharmacy.getDescription(), pharmacy.getAddress());
        return new ResponseEntity<>(dto, HttpStatus.OK);
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

