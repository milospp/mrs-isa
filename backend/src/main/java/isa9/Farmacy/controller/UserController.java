package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.service.*;
import isa9.Farmacy.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final MedReservationService medReservationService;
    private final RatingService ratingService;
    private final ExaminationService examinationService;

    private final PatientToPatientDTO patientToPatientDTO;
    private final MedicineToMedicineDTO medicineToMedicineDTO;
    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;
    private final PenalityToPenalityDTO penalityToPenalityDTO;
    private final DermatologistToDermatologistDTO dermatologistToDermatologistDTO;
    private final PharmacistToPharmacistDTO pharmacistToPharmacistDTO;
    private final MedReservationToMedReservationDTO medReservationToMedReservationDTO;
    private final DoctorToDoctorDTO doctorToDoctorDTO;
    private final UserToUserDTO userToUserDTO;
    private final RatingToRatingDTO ratingToRatingDTO;

    private final UserDTOToUser userDTOToUser;

    @Autowired
    public UserController(UserService userService, PharmacyService pharmacyService, MedReservationService medReservationService, RatingService ratingService, PatientToPatientDTO patientToPatientDTO, MedicineToMedicineDTO medicineToMedicineDTO, PharmacyToPharmacyDTO pharmacyToPharmacyDTO, PenalityToPenalityDTO penalityToPenalityDTO, DermatologistToDermatologistDTO dermatologistToDermatologistDTO, PharmacistToPharmacistDTO pharmacistToPharmacistDTO, MedReservationToMedReservationDTO medReservationToMedReservationDTO, DoctorToDoctorDTO doctorToDoctorDTO, UserToUserDTO userToUserDTO, RatingToRatingDTO ratingToRatingDTO, UserDTOToUser userDTOToUser, ExaminationService examinationService) {
        this.userService = userService;
        this.pharmacyService = pharmacyService;
        this.medReservationService = medReservationService;
        this.ratingService = ratingService;
        this.patientToPatientDTO = patientToPatientDTO;
        this.medicineToMedicineDTO = medicineToMedicineDTO;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
        this.penalityToPenalityDTO = penalityToPenalityDTO;
        this.dermatologistToDermatologistDTO = dermatologistToDermatologistDTO;
        this.pharmacistToPharmacistDTO = pharmacistToPharmacistDTO;
        this.medReservationToMedReservationDTO = medReservationToMedReservationDTO;
        this.doctorToDoctorDTO = doctorToDoctorDTO;
        this.userToUserDTO = userToUserDTO;
        this.ratingToRatingDTO = ratingToRatingDTO;
        this.userDTOToUser = userDTOToUser;
        this.examinationService = examinationService;
    }

    @GetMapping("tmp-test")
    public ResponseEntity<Boolean> debug(){

        RatingDoctor rate = new RatingDoctor(null, userService.findOne(1L), 5, userService.getDoctorById(16L));
        System.out.println("rate = " + rate);
        ratingService.save(rate);

        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> resultDTOS = new ArrayList<>();
        for (User user : this.userService.findAll()){
            resultDTOS.add(new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole(), user.getEmail()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.findOne(id);

//        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress().toString(), user.getPhoneNumber(), user.getRole());
        UserDTO dto;
//        if (user.getRole() == UserRole.PATIENT){
//            dto = (UserDTO) patientToPatientDTO.convert((Patient) user);
//        } else if (user.getRole() == UserRole.PHARMACIST){
//            dto = (UserDTO) pharmacistToPharmacistDTO.convert((Pharmacist) user);
//            System.out.println(user.getEmail());
//        } else {
//            dto = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole(), user.getEmail());
        dto = userToUserDTO.convert(user);
        System.out.println(user.getEmail());
//        }
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("{id}/allergies")
    public ResponseEntity<List<MedicineDTO>> getUserAlergies(@PathVariable Long id){
        Set<Medicine> allergies = userService.getPatientAllergies(id);
        List<MedicineDTO> dto = medicineToMedicineDTO.convert(allergies);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("doctor/{id}/rating")
    public ResponseEntity<DermatologistDTO> getMyDermatologistRating(@PathVariable Long id){
        User user = userService.findOne(id);
        double rating = ratingService.getDoctorAverage(id);

        DermatologistDTO dto = dermatologistToDermatologistDTO.convert((Dermatologist) user);
        dto.setRating(rating);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("doctor/{doctorId}/rating/user/{userId}")
    public ResponseEntity<RatingDTO> getUserRatingValue(@PathVariable Long doctorId, @PathVariable Long userId){
        Rating rating = ratingService.getPatientDoctorRate(userId, doctorId);

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("doctor/{id}/rating")
    public ResponseEntity<RatingDTO> rateDoctor(@PathVariable Long id, @RequestBody RatingDTO ratingDTO){
        Rating rating = ratingService.rateDoctor(id, ratingDTO.getUser(), ratingDTO.getRating());

        RatingDTO dto = ratingToRatingDTO.convert(rating);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("{id}/allergies")
    public ResponseEntity<MedicineDTO> getUserAlergies(@RequestBody MedicineDTO medicine){
        // TODO: Get patient from session
        User user = userService.findOne(1L);
        Patient patient = (Patient) user;

        Medicine allergy = userService.addPatientAllergy(patient, medicine.getId());
        MedicineDTO dto = medicineToMedicineDTO.convert(allergy);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @DeleteMapping("{id}/allergies")
    public ResponseEntity<Void> deleteUserAlergies(@RequestBody MedicineDTO medicine){
        // TODO: Get patient from session
        User user = userService.findOne(1L);
        Patient patient = (Patient) user;

        userService.removePatientAllergy(patient, medicine.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("{id}/reservations")
    public ResponseEntity<List<MedReservationDTO>> getUserReservations(@PathVariable Long id){
        User user = userService.findOne(id);
        Set<MedReservation> reservations = userService.getPatientReservations(id);

        List<MedReservationDTO> dto = medReservationToMedReservationDTO.convert(reservations);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("reservations/{reservationId}/cancel")
    public ResponseEntity<MedReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        MedReservation medReservation = medReservationService.cancel(reservationId);
        MedReservationDTO dto = medReservationToMedReservationDTO.convert(medReservation);
        if (medReservation.getStatus() != MedReservationStatus.CANCELED)
            return new ResponseEntity<MedReservationDTO>(dto, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<MedReservationDTO>(dto, HttpStatus.OK);
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
                new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), user.getRole(), user.getEmail()),
                HttpStatus.OK
        );

    }

    @PostMapping("/register/pharmacist/{id}")
    public ResponseEntity<Integer> createPharmacist(@PathVariable Long id, @RequestBody WorkerHelp user) {
        int povratna = 0;
        if (!userService.isAvaibleEmail(user.getRegisterData().getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        User adminUser = userService.findOne(id);
        if (adminUser.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        user.getRegisterData().setWorking(new HashSet<>());
        Work w = new Work();
        w.setDoctor(user.getRegisterData());
        w.setPharmacy(((PharmacyAdmin) adminUser).getPharmacy());
        w.setStartHour(LocalTime.parse(user.getStartHour()));
        w.setEndHour(LocalTime.parse(user.getEndHour()));
        user.getRegisterData().getWorking().add(w);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        userService.save(user.getRegisterData());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    //@RequestMapping(value="/patients", produces="application/json", consumes="application/json")
    @PostMapping("/patients")
    public ResponseEntity<PatientsPagesDTO> getAllPatients(@RequestBody PaginationSortSearchDTO pssDTO) {

        System.out.println(pssDTO.getPageNo() + pssDTO.getPageSize() + pssDTO.getSortBy()
                + pssDTO.getSearchParams().get("doctorId") + pssDTO.isAscending()
                + pssDTO.getSearchParams().get("name") + pssDTO.getSearchParams().get("surname"));

        List<Patient> list = userService.getAllMyPatientsPaged(pssDTO);

        List<PatientDTO> convertedList = patientToPatientDTO.convert(list);

        long totalCount = userService.getAllMyPatientsTotalCount(pssDTO);

        return new ResponseEntity<>(new PatientsPagesDTO(totalCount, convertedList), HttpStatus.OK);
    }

    @GetMapping("all-patients")
    public ResponseEntity<List<PatientDTO>> getAllPatientsDTO() {
        List<PatientDTO> resultDTOS = new ArrayList<>();
        List<User> svi = userService.findAll();
        for (User us : svi){
            if (us.getClass()!= Patient.class) continue;
            Patient patient = (Patient) us;
            resultDTOS.add(patientToPatientDTO.convert(patient));
//            resultDTOS.add(new PatientDTO(patient.getId(), patient.getName(), patient.getSurname(), patient.getAddress(), patient.getPhoneNumber()));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        User us = userService.findOne(id);
        if (us.getClass() != Patient.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Patient patient = (Patient) us;
        PatientDTO patientDTO = patientToPatientDTO.convert(patient);
        System.out.println("patientDTO = " + patientDTO);
//        PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getName(), patient.getSurname(), patient.getAddress(), patient.getPhoneNumber());
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

    @PostMapping("{id}/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patient) {
        PatientDTO patientDTO = patientToPatientDTO.convert(userService.updatePatient(patient));
        return new ResponseEntity<> (patientDTO, HttpStatus.OK);
    }

    @PostMapping("register/pharmacyAdmin")
    public ResponseEntity<Boolean> registerPharmacyAdmin(@RequestBody PharmacyAdminRegDTO newAdminDto){
        if(!userService.isAvaibleEmail(newAdminDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        long pharmacyId = newAdminDto.getPharmacyId();
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        PharmacyAdmin newlyRegistered = new PharmacyAdmin(0L, newAdminDto.getName(), newAdminDto.getSurname(), newAdminDto.getEmail(),
                newAdminDto.getPassword(), newAdminDto.getAddress(), newAdminDto.getPhoneNumber(), UserRole.PHARMACY_ADMIN, pharmacy);
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
                                                phAdmin.getAddress(), phAdmin.getPhoneNumber(), phAdmin.getEmail(),
                                                phAdmin.getPharmacy().getId()));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("/pharmacists/admin/{id}")
     public ResponseEntity<List<PharmacistDTO>> getAllPharmacistsAdmin(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId()
                    .equals(admin.getPharmacy().getId()))
                povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pharmacists/admin/{id}/{search}")
    public ResponseEntity<List<PharmacistDTO>> searchPharmacistsAdmin(@PathVariable Long id, @PathVariable String search) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId()
                    .equals(admin.getPharmacy().getId()))
                if (farmaceut.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                        farmaceut.getName().toLowerCase().contains(search.toLowerCase())) {
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharmacists/admin/{id}")
    public ResponseEntity<List<PharmacistDTO>> filterPharmacistsAdmin(@PathVariable Long id, @RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId()
                    .equals(admin.getPharmacy().getId()))
                if (farmaceut.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                        farmaceut.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase()) ||
                        farmaceut.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                        farmaceut.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                        farmaceut.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                        farmaceut.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                        farmaceut.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                        farmaceut.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                        farmaceut.getPhoneNumber().toLowerCase().contains(podaci.getFilterBroj().toLowerCase())) {
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pharmacists/pharmacy/{id}")
    public ResponseEntity<List<PharmacistDTO>> getAllPharmacistsPharmacy(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(id))
                povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharmacist/fire/{idAdmina}")
    public ResponseEntity<Integer> firePharmacist(@PathVariable Long idAdmina, @RequestBody PharmacistDTO zaBrisanje) {
        List<User> svi = userService.findAll();
        int okej = 1;
        if (userService.findOne(idAdmina).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(-1, HttpStatus.OK);
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(idAdmina);
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            List<Examination> sviPregledi = this.examinationService.getPharmacistFutureExaminations(farmaceut.getId());
            if (farmaceut.getName().equals(zaBrisanje.getName()) && farmaceut.getSurname().equals(zaBrisanje.getSurname())
                && !farmaceut.getWorking().isEmpty()
                && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(admin.getPharmacy().getId()))
                    if (sviPregledi.isEmpty()) {
                        farmaceut.setWorking(new HashSet<>());
                        okej = 0;
                        userService.save(farmaceut);
                        break;
                    }
        }
        return new ResponseEntity<>(okej, HttpStatus.OK);
    }

    @GetMapping("/pharmacists/pharmacy/{id}/{search}")
    public ResponseEntity<List<PharmacistDTO>> searchPharmacistsPharmacy(@PathVariable Long id, @PathVariable String search) {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(id))
                if (farmaceut.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                        farmaceut.getName().toLowerCase().contains(search.toLowerCase())) {
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharmacists/pharmacy/{id}")
    public ResponseEntity<List<PharmacistDTO>> filterPharmacistsPharmacy(@PathVariable Long id, @RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(id))
                if (farmaceut.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                        farmaceut.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                        farmaceut.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                        farmaceut.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                        farmaceut.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                        farmaceut.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                        farmaceut.getPhoneNumber().contains(podaci.getFilterBroj()) ||
                        farmaceut.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                        farmaceut.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase())) {
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/admin/{id}")
    public ResponseEntity<List<DermatologistDTO>> getAllDermatologistsAdmin(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(admin.getPharmacy().getId())) {
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/admin/{id}/{search}")
    public ResponseEntity<List<DermatologistDTO>> searchDermatologistsAdmin(@PathVariable Long id, @PathVariable String search) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking()) {
                if (work.getPharmacy().getId().equals(admin.getPharmacy().getId())) {
                    if (dermatolog.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                            dermatolog.getName().toLowerCase().contains(search.toLowerCase())) {
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    }
                    break;
                }}
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/dermatologists/admin/{id}")
    public ResponseEntity<List<DermatologistDTO>> filterDermatologistsAdmin(@PathVariable Long id, @RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking()) {
                if (work.getPharmacy().getId().equals(admin.getPharmacy().getId())) {
                      if (dermatolog.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                            dermatolog.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase()) ||
                            dermatolog.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                            dermatolog.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                            dermatolog.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                            dermatolog.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                            dermatolog.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                            dermatolog.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                            dermatolog.getPhoneNumber().toLowerCase().contains(podaci.getFilterBroj().toLowerCase())) {
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    }
                    break;
                }}
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/dermatologists/pharmacy/{id}")
    public ResponseEntity<List<DermatologistDTO>> filterDermatologistsPharmacy(@PathVariable Long id, @RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(id)) {
                    if (dermatolog.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                            dermatolog.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase()) ||
                            dermatolog.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                            dermatolog.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                            dermatolog.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                            dermatolog.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                            dermatolog.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                            dermatolog.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                            dermatolog.getPhoneNumber().toLowerCase().contains(podaci.getFilterBroj().toLowerCase())) {
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    }
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }


    @GetMapping("/dermatologists/pharmacy/{id}/{search}")
    public ResponseEntity<List<DermatologistDTO>> getAllDermatologistsPharmacy(@PathVariable Long id, @PathVariable String search) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(id)) {
                    if (dermatolog.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                            dermatolog.getName().toLowerCase().contains(search.toLowerCase())) {
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    }
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/pharmacy/{id}")
    public ResponseEntity<List<DermatologistDTO>> getAllDermatologistsPharmacy(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(id)) {
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("register/supplier")
    public ResponseEntity<Boolean> registerSupplier(@RequestBody SupplierRegistrationDTO newSupplierDto){
        if(!userService.isAvaibleEmail(newSupplierDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        Supplier newlyRegistered = new Supplier(0L, newSupplierDto.getName(), newSupplierDto.getSurname(),
                newSupplierDto.getEmail(), newSupplierDto.getPassword(), newSupplierDto.getAddress(),
                newSupplierDto.getPhoneNumber());
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("register/dermatologist")
    public ResponseEntity<Boolean> registerDermatologist(@RequestBody DermatologistRegDTO newDermatologistDto){
        if(!userService.isAvaibleEmail(newDermatologistDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        Dermatologist newlyRegistered = new Dermatologist(0L, newDermatologistDto.getName(), newDermatologistDto.getSurname(),
                newDermatologistDto.getEmail(), newDermatologistDto.getPassword(),
                newDermatologistDto.getAddress(), newDermatologistDto.getPhoneNumber(), new HashSet<>());
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("edit/dermatologist")
    public ResponseEntity<Boolean> editDermatologistData(@RequestBody DermatologistDTO dermatologistDTO){
        Doctor doctor = userService.getDoctorById(dermatologistDTO.getId());
        if (doctor.getRole() == UserRole.DERMATOLOGIST){
            Dermatologist dermatologist = (Dermatologist) doctor;
            dermatologist.setName(dermatologistDTO.getName());
            dermatologist.setSurname(dermatologistDTO.getSurname());
            if (dermatologist.getAddress() != dermatologistDTO.getAddress()){
                Address newAdress = Address.builder()
                        .id(null)
                        .state(dermatologistDTO.getAddress().getState())
                        .city(dermatologistDTO.getAddress().getCity())
                        .street(dermatologistDTO.getAddress().getStreet())
                        .number(dermatologistDTO.getAddress().getNumber())
                        .build();
                dermatologist.setAddress(newAdress);
            }
            //dermatologist.setAddress(dermatologistDTO.getAddress());  // not using this because it changes someone else's address also
            dermatologist.setEmail(dermatologistDTO.getEmail());
            dermatologist.setPhoneNumber(dermatologistDTO.getPhoneNumber());
            userService.save(dermatologist);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("edit/pharmacist")
    public ResponseEntity<Boolean> editPharmacistData(@RequestBody PharmacistDTO pharmacistDTO){
        Pharmacist pharmacist = (Pharmacist) userService.getDoctorById(pharmacistDTO.getId());
        pharmacist.setName(pharmacistDTO.getName());
        pharmacist.setSurname(pharmacistDTO.getSurname());
        if (pharmacist.getAddress() != pharmacistDTO.getAddress()){
            Address newAdress = Address.builder()
                    .id(null)
                    .state(pharmacistDTO.getAddress().getState())
                    .city(pharmacistDTO.getAddress().getCity())
                    .street(pharmacistDTO.getAddress().getStreet())
                    .number(pharmacistDTO.getAddress().getNumber())
                    .build();
            pharmacist.setAddress(newAdress);
        }
        //pharmacist.setAddress(pharmacistDTO.getAddress()); // not using this because it changes someone else's address also
        pharmacist.setEmail(pharmacistDTO.getEmail());
        pharmacist.setPhoneNumber(pharmacistDTO.getPhoneNumber());
        userService.save(pharmacist);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
