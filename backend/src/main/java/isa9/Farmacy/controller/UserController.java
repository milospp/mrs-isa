package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.support.MedicineToMedicineDTO;
import isa9.Farmacy.support.PatientToPatientDTO;
import isa9.Farmacy.support.PenalityToPenalityDTO;
import isa9.Farmacy.support.PharmacyToPharmacyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Flow;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PharmacyService pharmacyService;

    private final PatientToPatientDTO patientToPatientDTO;
    private final MedicineToMedicineDTO medicineToMedicineDTO;
    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;
    private final PenalityToPenalityDTO penalityToPenalityDTO;

    @Autowired
    public UserController(UserService userService,
                          PharmacyService pharmacyService,
                          PatientToPatientDTO patientToPatientDTO,
                          MedicineToMedicineDTO medicineToMedicineDTO,
                          PharmacyToPharmacyDTO pharmacyToPharmacyDTO,
                          PenalityToPenalityDTO penalityToPenalityDTO){
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.patientToPatientDTO = patientToPatientDTO;
        this.medicineToMedicineDTO = medicineToMedicineDTO;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
        this.penalityToPenalityDTO = penalityToPenalityDTO;
    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> resultDTOS = new ArrayList<>();
        for (User user : this.userService.findAll()){
            resultDTOS.add(new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.findOne(id);

//        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress().toString(), user.getPhoneNumber(), user.getRole());
        UserDTO dto;
        if (user.getRole() == UserRole.PATIENT){
            dto = (UserDTO) patientToPatientDTO.convert((Patient) user);
        } else {
            dto = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole());
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{id}/allergies")
    public ResponseEntity<List<MedicineDTO>> getUserAlergies(@PathVariable Long id){
        User user = userService.findOne(id);
        Set<Medicine> allergies = userService.getPatientAllergies(user);

        List<MedicineDTO> dto = medicineToMedicineDTO.convert(allergies);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{id}/subscriptions")
    public ResponseEntity<List<PharmacyDTO>> getUserSubscriptions(@PathVariable Long id){
        User user = userService.findOne(id);
        Set<Pharmacy> subscriptions = userService.getPatientSubscriptions(user);

        List<PharmacyDTO> dto = pharmacyToPharmacyDTO.convert(subscriptions);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{id}/penalties")
    public ResponseEntity<List<PenalityDTO>> getUserPenalties(@PathVariable Long id){
        User user = userService.findOne(id);
        Set<Penality> penalties = userService.getPenalties(user);

        List<PenalityDTO> dto = penalityToPenalityDTO.convert(penalties);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{id}/penalties/count")
    public ResponseEntity<Integer> getUserPenaltiesCount(@PathVariable Long id){
        User user = userService.findOne(id);
        Integer penalties = userService.countActivePenalties(user);

        return new ResponseEntity<>(penalties, HttpStatus.OK);

    }


    @DeleteMapping("{id}/subscriptions/{pharmacyId}")
    public ResponseEntity<List<PharmacyDTO>> getUserSubscriptions(@PathVariable Long id, @PathVariable Long pharmacyId){
        User user = userService.findOne(id);
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);

        userService.PatientUnsubscribe(user, pharmacy);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(
                new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole()),
                HttpStatus.OK
        );

    }

    @PostMapping("/register/pharmacist")
    public ResponseEntity<Integer> createPharmacist(@RequestBody Pharmacist user) {
        int povratna = 0;
        if (!userService.isAvaibleEmail(user.getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        User createdUser = userService.save(user);
        System.out.println(createdUser);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @RequestMapping(value="/patients", produces="application/json")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<User> svi = userService.findAll();
        List<Patient> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Patient.class) povratna.add((Patient) u);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("all-patients")
    public ResponseEntity<List<PatientDTO>> getAllPatientsDTO() {
        List<PatientDTO> resultDTOS = new ArrayList<>();
        List<User> svi = userService.findAll();
        for (User us : svi){
            if (us.getClass()!= Patient.class) continue;
            Patient patient = (Patient) us;
            resultDTOS.add(new PatientDTO(patient.getId(), patient.getName(), patient.getSurname(), patient.getAddress(), patient.getPhoneNumber()));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        User us = userService.findOne(id);
        if (us.getClass() != Patient.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Patient patient = (Patient) us;
        PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getName(), patient.getSurname(), patient.getAddress(), patient.getPhoneNumber());
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);

    }


    @PostMapping("register/patient")
    public ResponseEntity<Boolean> registerUser(@RequestBody PatientRegistrationDTO patient) {
        int povratna = 0;
        if (!userService.isAvaibleEmail(patient.getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(false, HttpStatus.OK);
        Patient newlyRegistered = new Patient(patient.getId(), patient.getName(), patient.getSurname()
                , patient.getEmail(), patient.getPassword(),
                patient.getAddress(), patient.getPhoneNumber());
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<> (true,
                HttpStatus.OK
        );

    }

    @PostMapping("register/pharmacyAdmin")
    public ResponseEntity<Boolean> registerPharmacyAdmin(@RequestBody PharmacyAdminRegDTO newAdminDto){
        if(!userService.isAvaibleEmail(newAdminDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        long pharmacyId = newAdminDto.getPharmacyId();
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        PharmacyAdmin newlyRegistered = new PharmacyAdmin(0L, newAdminDto.getName(), newAdminDto.getSurname(), newAdminDto.getEmail(),
                newAdminDto.getPassword(), pharmacy, newAdminDto.getAddress(), newAdminDto.getPhoneNumber());
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("all-pharmacy-admins")
    public ResponseEntity<List<PharmacyAdminDTO>> getAllPharmacyAdminsDTO() {
        List<PharmacyAdminDTO> resultDTOS = new ArrayList<>();
        List<User> allUsers = userService.findAll();
        for (User us : allUsers){
            if (us.getClass()!= PharmacyAdmin.class) continue;
            PharmacyAdmin phAdmin = (PharmacyAdmin) us;
            resultDTOS.add(new PharmacyAdminDTO(phAdmin.getId(), phAdmin.getName(), phAdmin.getSurname(),
                                                phAdmin.getAddress(), phAdmin.getPhoneNumber(),
                                                phAdmin.getPharmacy().getId()));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }


}
