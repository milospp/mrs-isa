package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.WorkToWorkDTO;
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
    private final UserService userService;
    private final WorkToWorkDTO workToWorkDTO;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService, UserService userService, WorkToWorkDTO workToWorkDTO){
        this.pharmacyService = pharmacyService;
        this.userService = userService;
        this.workToWorkDTO = workToWorkDTO;
    }


    @GetMapping("")
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> resultDTOS = new ArrayList<>();
        for (Pharmacy p : this.pharmacyService.findAll()){
            resultDTOS.add(new PharmacyDTO(p.getId(), p.getName(), p.getDescription(), p.getAddress()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    /*Returns all pharmacies that don't have a pharmacy administrator assigned to them*/
    @GetMapping("available")
    public ResponseEntity<List<PharmacyDTO>> getAvailablePharmacies(){
        List<PharmacyDTO> resultDTOS = new ArrayList<>();
        for (Pharmacy p : this.pharmacyService.findAll()){
            if(userService.findPharmacyAdmin(p.getId()) == null) {
                resultDTOS.add(new PharmacyDTO(p.getId(), p.getName(), p.getDescription(), p.getAddress()));
            }
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

    @GetMapping("working/{doctorId}")
    public ResponseEntity<List<WorkDTO>> getPharmacistPharmacy(@PathVariable Long doctorId) {
        User doctor = userService.findOne(doctorId);
        System.out.println("Looking for jobs");

        if (doctor.getRole() == UserRole.PHARMACIST || doctor.getClass() == Pharmacist.class){
            System.out.println("Look! Pharmacist!");
            Pharmacist p = (Pharmacist) doctor;
            System.out.println("Look! REAL Pharmacist!");
            List<WorkDTO> work = this.workToWorkDTO.convert(p.getWorking());
            System.out.println("Look! " + p.getWorking().get(0).toString());
            return new ResponseEntity<>(work, HttpStatus.OK);
        }
        else if (doctor.getRole() == UserRole.DERMATOLOGIST || doctor.getClass() == Dermatologist.class){
            System.out.println("Look! Dermatologist!");
            Dermatologist d = (Dermatologist) doctor;
            List<WorkDTO> work = this.workToWorkDTO.convert(d.getWorking());
            return new ResponseEntity<>(work, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

//        if (pharmacy == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        PharmacyDTO dto = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(),
//                pharmacy.getDescription(), pharmacy.getAddress());
//        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

