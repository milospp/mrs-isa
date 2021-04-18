package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DermatologistDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.PharmacyToPharmacyDTO;
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
            PharmacyAdmin phAdmin = userService.findPharmacyAdmin(p.getId());
            if(phAdmin == null) {
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
        Doctor doctor = userService.getDoctorById(doctorId);
        List<Work> work = pharmacyService.findDoctorsWork(doctor);
        List<WorkDTO> convertedWork = this.workToWorkDTO.convert(work);
        return new ResponseEntity<>(convertedWork, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<PharmacyDTO> getPharmacyAdmin(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Pharmacy apoteka = ((PharmacyAdmin) user).getPharmacy();
        return new ResponseEntity<>((new PharmacyToPharmacyDTO()).convert(apoteka), HttpStatus.OK);
    }

    @PostMapping("/setPharmacyInfo")
    public ResponseEntity<Boolean> setPharmacyInfo(@RequestBody PharmacyDTO apotekaDTO) {
        Pharmacy apoteka = pharmacyService.findOne(apotekaDTO.getId());
        Pharmacy apoteka2 = null;
        // inace nepotrebno
        for (User u : userService.findAll()) {
            if (u.getClass() != PharmacyAdmin.class) continue;
            PharmacyAdmin admin = (PharmacyAdmin) u;
            if (admin.getPharmacy().getId() != apoteka.getId()) continue;
            apoteka2 = admin.getPharmacy();
            break;
        }
        // kraj inace nepotrebnog koda
        apoteka.setName(apotekaDTO.getName());
        apoteka.setDescription(apoteka.getDescription());
        if (apoteka2 != null) {
            apoteka2.setName(apotekaDTO.getName());
            apoteka2.setDescription(apoteka.getDescription());
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

