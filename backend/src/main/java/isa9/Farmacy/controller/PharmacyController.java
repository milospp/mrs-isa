package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedicineDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.model.dto.RatingDTO;
import isa9.Farmacy.model.dto.WorkDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.RatingService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.PharmacyToPharmacyDTO;
import isa9.Farmacy.support.RatingToRatingDTO;
import isa9.Farmacy.support.WorkToWorkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final RatingService ratingService;
    private final UserService userService;
    private final WorkToWorkDTO workToWorkDTO;
    private final RatingToRatingDTO ratingToRatingDTO;
    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService, RatingService ratingService, UserService userService, WorkToWorkDTO workToWorkDTO, RatingToRatingDTO ratingToRatingDTO, PharmacyToPharmacyDTO pharmacyToPharmacyDTO){
        this.pharmacyService = pharmacyService;
        this.ratingService = ratingService;
        this.userService = userService;
        this.workToWorkDTO = workToWorkDTO;
        this.ratingToRatingDTO = ratingToRatingDTO;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
    }


    @GetMapping("")
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> resultDTOS = pharmacyToPharmacyDTO.convert(this.pharmacyService.findAll());
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    /*Returns all pharmacies that don't have a pharmacy administrator assigned to them*/
    @GetMapping("available")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<PharmacyDTO>> getAvailablePharmacies(){
        List<PharmacyDTO> resultDTOS = new ArrayList<>();

        for (Pharmacy p : this.pharmacyService.findAll()){
            PharmacyAdmin phAdmin = userService.findPharmacyAdmin(p.getId());
            if(phAdmin == null) {
                resultDTOS.add(pharmacyToPharmacyDTO.convert(p));
            }
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }
  
    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable Long id) {
        Pharmacy pharmacy = pharmacyService.findOne(id);

        if (pharmacy == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        PharmacyDTO dto = pharmacyToPharmacyDTO.convert(pharmacy);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PostMapping("register/pharmacy")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> registerPharmacy(@RequestBody PharmacyDTO pDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


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

        System.out.println(work.get(0).getStartHour());
        System.out.println(work.get(0).getEndHour());
        System.out.println(work.get(1).getStartHour());
        System.out.println(work.get(1).getEndHour());

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

    @GetMapping("{id}/rating")
    public ResponseEntity<PharmacyDTO> getMedicineRating(@PathVariable Long id){
        Pharmacy pharmacy = pharmacyService.findOne(id);
        PharmacyDTO dto = pharmacyToPharmacyDTO.convert(pharmacy);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("{pharmacyId}/rating/user/{userId}")
    public ResponseEntity<RatingDTO> getUserRatingValue(@PathVariable Long pharmacyId, @PathVariable Long userId){
        Rating rating = ratingService.getPatientPharmacyRate(userId, pharmacyId);

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("{id}/rating")
    public ResponseEntity<RatingDTO> ratePharmacy(@PathVariable Long id, @RequestBody RatingDTO ratingDTO){
        Rating rating = ratingService.ratePharmacy(id, ratingDTO.getUser(), ratingDTO.getRating());

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("{pharmacyId}/rating/user/{userId}/can-rate")
    public ResponseEntity<Boolean> getCanUserRate(@PathVariable Long pharmacyId, @PathVariable Long userId){
        Boolean result = ratingService.canUserRate(userId, pharmacyId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

