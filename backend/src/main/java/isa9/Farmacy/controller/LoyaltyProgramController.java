package isa9.Farmacy.controller;

import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.dto.ExaminationPointsDTO;
import isa9.Farmacy.model.dto.LoyaltyProgramDTO;
import isa9.Farmacy.service.LoyaltyProgramService;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.LoyaltyDTOtoLoyalty;
import isa9.Farmacy.support.LoyaltyToLoyaltyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/loyaltyPrograms")
public class LoyaltyProgramController {

    private final LoyaltyProgramService loyaltyProgramService;
    private final UserService userService;
    private final MedReservationService medReservationService;
    private final LoyaltyToLoyaltyDTO loyaltyToLoyaltyDTO;
    private final LoyaltyDTOtoLoyalty loyaltyDTOtoLoyalty;

    @Autowired
    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService, UserService userService, MedReservationService medReservationService, LoyaltyToLoyaltyDTO loyaltyToLoyaltyDTO, LoyaltyDTOtoLoyalty loyaltyDTOtoLoyalty) {
        this.loyaltyProgramService = loyaltyProgramService;
        this.userService = userService;
        this.medReservationService = medReservationService;
        this.loyaltyToLoyaltyDTO = loyaltyToLoyaltyDTO;
        this.loyaltyDTOtoLoyalty = loyaltyDTOtoLoyalty;
    }

    @PostMapping("/newUserCategory")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Integer> addUserCategory(@RequestBody LoyaltyProgramDTO dto){
        LoyaltyProgram loyaltyProgram = this.loyaltyDTOtoLoyalty.convert(dto);
        return new ResponseEntity<>(this.loyaltyProgramService.saveNewUserCategory(loyaltyProgram), HttpStatus.OK);
    }

    @PostMapping("/updateCategory")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Integer> editUserCategory(@RequestBody LoyaltyProgramDTO update){
        LoyaltyProgram updatedCategory = this.loyaltyDTOtoLoyalty.convert(update);
        return new ResponseEntity<>(this.loyaltyProgramService.updateUserCategory(updatedCategory), HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<List<LoyaltyProgramDTO>> getAllCategories(){
        return new ResponseEntity<>(this.loyaltyToLoyaltyDTO.convert(this.loyaltyProgramService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/examinationPoints")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Integer> getExaminationPointsReward(){
        return new ResponseEntity<>(this.loyaltyProgramService.getExaminationPointsReward(), HttpStatus.OK);
    }

    @PostMapping("/setExaminationPointsReward")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Integer> setExaminationPointsReward(@RequestBody ExaminationPointsDTO dto){
        return new ResponseEntity<>(this.loyaltyProgramService.setExaminationPointsReward(dto.getPoints()), HttpStatus.OK);
    }

    @GetMapping("/categoryOfPatient/{id}")
    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('SYS_ADMIN')")
    public ResponseEntity<LoyaltyProgramDTO> getCategoryOfPatient(@PathVariable Long id){
        Patient patient = (Patient) this.userService.findOne(id);

        return new ResponseEntity<>( this.loyaltyToLoyaltyDTO.convert(this.loyaltyProgramService.getCategoryOfUser(patient)), HttpStatus.OK);
    }
}
