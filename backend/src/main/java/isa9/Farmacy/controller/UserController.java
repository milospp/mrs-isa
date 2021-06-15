package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;

import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserRoleService;
import isa9.Farmacy.service.UserService;

import isa9.Farmacy.service.*;

import isa9.Farmacy.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final MedReservationService medReservationService;

    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    

    private final RatingService ratingService;
    private final ExaminationService examinationService;
    private final AppointmentService appointmentService;

    private final PatientToPatientDTO patientToPatientDTO;
    private final MedicineToMedicineDTO medicineToMedicineDTO;
    private final PharmacyToPharmacyDTO pharmacyToPharmacyDTO;
    private final PenalityToPenalityDTO penalityToPenalityDTO;
    private final DermatologistToDermatologistDTO dermatologistToDermatologistDTO;
    private final PharmacistToPharmacistDTO pharmacistToPharmacistDTO;
    private final MedReservationToMedReservationDTO medReservationToMedReservationDTO;
    private final AppointmentToAppointmentDTO appointmentToAppointmentDTO;
    private final DoctorToDoctorDTO doctorToDoctorDTO;
    private final UserToUserDTO userToUserDTO;
    private final RatingToRatingDTO ratingToRatingDTO;

    private final UserDTOToUser userDTOToUser;

    @Autowired

    public UserController(UserService userService, PharmacyService pharmacyService, MedReservationService medReservationService, RatingService ratingService, PatientToPatientDTO patientToPatientDTO, MedicineToMedicineDTO medicineToMedicineDTO, PharmacyToPharmacyDTO pharmacyToPharmacyDTO, PenalityToPenalityDTO penalityToPenalityDTO, DermatologistToDermatologistDTO dermatologistToDermatologistDTO, PharmacistToPharmacistDTO pharmacistToPharmacistDTO, MedReservationToMedReservationDTO medReservationToMedReservationDTO, AppointmentToAppointmentDTO appointmentToAppointmentDTO, DoctorToDoctorDTO doctorToDoctorDTO, UserToUserDTO userToUserDTO, RatingToRatingDTO ratingToRatingDTO, UserDTOToUser userDTOToUser, ExaminationService examinationService, UserRoleService urs, PasswordEncoder pe, AppointmentService appointmentService) {
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
        this.appointmentToAppointmentDTO = appointmentToAppointmentDTO;
        this.doctorToDoctorDTO = doctorToDoctorDTO;
        this.userToUserDTO = userToUserDTO;

        this.passwordEncoder = pe;
        this.userRoleService = urs;
        this.ratingToRatingDTO = ratingToRatingDTO;
        this.userDTOToUser = userDTOToUser;
        this.examinationService = examinationService;

        this.appointmentService = appointmentService;
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
            RolesDTO role = RolesDTO.SYS_ADMIN;
            int roleId = user.getRole().getId().intValue();
            switch(roleId){
                case 1:
                    role = RolesDTO.SYS_ADMIN;
                    break;
                case 2:
                    role = RolesDTO.PHARMACY_ADMIN;
                    break;
                case 3:
                    role = RolesDTO.PATIENT;
                    break;
                case 4:
                    role = RolesDTO.DERMATOLOGIST;
                    break;
                case 5:
                    role = RolesDTO.PHARMACIST;
                    break;
                case 6:
                    role = RolesDTO.SUPPLIER;
                    break;
                default:
                    System.out.println("GRESKA ZEZNUO SAM DTO ULOGE");
            }
            resultDTOS.add(new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), role, user.getEmail()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.findOne(id);

        UserDTO dto;
        dto = userToUserDTO.convert(user);
        System.out.println(user.getEmail());
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
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        User user = userDTOToUser.convert(userDTO);
        user.setEnabled(false);
        user.setRole(this.userRoleService.findOne(3L));
        userService.save(user);
        return new ResponseEntity<>(
                new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getAddress(), user.getPhoneNumber(), RolesDTO.PATIENT, user.getEmail()),
                HttpStatus.OK
        );

    }

    @PostMapping("/register/pharmacist/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> createPharmacist(@PathVariable Long id, @RequestBody WorkerHelp user) {
        int povratna = 0;
        if (!userService.isAvaibleEmail(user.getRegisterData().getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        User adminUser = userService.findOne(id);
        if (adminUser.getClass() != PharmacyAdmin.class) return new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        user.getRegisterData().setEnabled(true);
        user.getRegisterData().setRole(this.userRoleService.findOne(5L));
        user.getRegisterData().setWorking(new HashSet<>());
        user.getRegisterData().setPassword(passwordEncoder.encode(user.getRegisterData().getPassword()));
        user.getRegisterData().setLastPasswordResetDate(null);
        user.getRegisterData().setWorking(new HashSet<>());
        Work posao = new Work();
        posao.setPharmacy(((PharmacyAdmin) adminUser).getPharmacy());
        posao.setStartHour( LocalTime.parse(user.getStartHour()));
        posao.setEndHour(LocalTime.parse(user.getEndHour()));
        posao.setDoctor(user.getRegisterData());
        user.getRegisterData().getWorking().add(posao);

        userService.save(user.getRegisterData());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/patients")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<PatientsPagesDTO> getAllPatients(@RequestBody PaginationSortSearchDTO pssDTO) {

        System.out.println(pssDTO.getPageNo() + pssDTO.getPageSize() + pssDTO.getSortBy()
                + pssDTO.getSearchParams().get("doctorId") + pssDTO.isAscending()
                + pssDTO.getSearchParams().get("name") + pssDTO.getSearchParams().get("surname"));

        PatientsPagesDTO response = userService.getPatientLastAppointmentDTOsSortedSearched(pssDTO);
        for (PatientLastAppointmentDTO pladto : response.getPatients()){
            Appointment last = appointmentService.findByStartTime(pladto.getLast());
            pladto.setAid(last.getId());
            pladto.setStatus(last.getExamination().getStatus());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("all-patients")
    public ResponseEntity<List<PatientDTO>> getAllPatientsDTO() {
        List<PatientDTO> resultDTOS = new ArrayList<>();
        List<User> svi = userService.findAll();
        for (User us : svi){
            if (us.getClass()!= Patient.class) continue;
            Patient patient = (Patient) us;
            resultDTOS.add(patientToPatientDTO.convert(patient));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        User us = userService.findOne(id);
        if (us.getClass() != Patient.class) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Patient patient = (Patient) us;
        PatientDTO patientDTO = patientToPatientDTO.convert(patient);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PostMapping("register/patient")
    public ResponseEntity<Boolean> registerUser(@RequestBody PatientRegistrationDTO patient) {
        int povratna = 0;
        if (!userService.isAvaibleEmail(patient.getEmail())) povratna += 2;
        if (povratna > 0) return new ResponseEntity<>(false, HttpStatus.OK);
        Patient newlyRegistered = new Patient(patient.getId(), patient.getName(), patient.getSurname()
                , patient.getEmail(), passwordEncoder.encode(patient.getPassword()), patient.getAddress(), patient.getPhoneNumber()
                , userRoleService.findOne(3L), false, null);
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<> (true, HttpStatus.OK);
    }

    @PostMapping("{id}/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patient) {
        PatientDTO patientDTO = patientToPatientDTO.convert(userService.updatePatient(patient));
        return new ResponseEntity<> (patientDTO, HttpStatus.OK);
    }

    @PostMapping("register/pharmacyAdmin")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> registerPharmacyAdmin(@RequestBody PharmacyAdminRegDTO newAdminDto){
        if(!userService.isAvaibleEmail(newAdminDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        long pharmacyId = newAdminDto.getPharmacyId();
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        PharmacyAdmin newlyRegistered = new PharmacyAdmin(0L, newAdminDto.getName(), newAdminDto.getSurname(), newAdminDto.getEmail(),
                passwordEncoder.encode(newAdminDto.getPassword()), newAdminDto.getAddress(), newAdminDto.getPhoneNumber(), userRoleService.findOne(2L), pharmacy);
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
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
     public ResponseEntity<List<PharmacistDTO>> getAllPharmacistsAdmin(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId()
                    .equals(admin.getPharmacy().getId())) {
                Work posao = null;
                for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pharmacists/admin/{id}/{search}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
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
                    Work posao = null;
                    for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharm/filter/admin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
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
                    Work posao = null;
                    for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
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
            if (!farmaceut.getWorking().isEmpty() && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(id)) {
                Work posao = null;
                for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
        }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/allPharmacists")
    public ResponseEntity<List<PharmacistDTO>> getAllPharmacists() {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            for (Work w : farmaceut.getWorking())
                povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, w));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharmacist/fire/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> firePharmacist(@PathVariable Long idAdmina, @RequestBody PharmacistDTO zaBrisanje) {
        List<User> svi = userService.findAll();
        int okej = 1;
        if (userService.findOne(idAdmina).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(-1, HttpStatus.OK);
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(idAdmina);
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            List<Examination> sviPregledi = this.examinationService.getFutureExaminations(farmaceut.getId());
            if (farmaceut.getName().equals(zaBrisanje.getName()) && farmaceut.getSurname().equals(zaBrisanje.getSurname())) {
                okej = 2;
                if (!farmaceut.getWorking().isEmpty()
                        && farmaceut.getWorking().iterator().next().getPharmacy().getId().equals(admin.getPharmacy().getId()))
                    if (sviPregledi.isEmpty()) {
                        farmaceut.setWorking(new HashSet<>());
                        okej = 0;
                        appointmentService.deleteFreeAppointments(farmaceut.getId());
                        userService.save(farmaceut);
                        break;
                    }
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
                    Work posao = null;
                    for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/pharmacists/search/{search}")
    public ResponseEntity<List<PharmacistDTO>> searchAllPharmacists(@PathVariable String search) {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (farmaceut.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                    farmaceut.getName().toLowerCase().contains(search.toLowerCase())) {
                for (Work w : farmaceut.getWorking())
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, w));
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/pharm/filter/pharmacy/{id}")
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
                    Work posao = null;
                    for (Work w : farmaceut.getWorking()) posao = w;    // on ima samo 1 posao
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, posao));
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }


    @PostMapping("pharmacists/filter")
    public ResponseEntity<List<PharmacistDTO>> filterAllPharmacists(@RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        List<PharmacistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Pharmacist.class) {
            Pharmacist farmaceut = (Pharmacist) u;
            if (farmaceut.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                    farmaceut.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                    farmaceut.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                    farmaceut.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                    farmaceut.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                    farmaceut.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                    farmaceut.getPhoneNumber().contains(podaci.getFilterBroj()) ||
                    farmaceut.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                    farmaceut.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase())) {
                for (Work w : farmaceut.getWorking())
                    povratna.add(this.pharmacistToPharmacistDTO.convert(farmaceut, w));
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/admin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
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
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/derm/hire/admin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<DermatologistDTO>> getHireDermatologistsAdmin(@PathVariable Long id) {
        List<User> svi = userService.findAll();
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(null, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Work posao = null;
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(admin.getPharmacy().getId())) { posao = work; break;}
            if (posao == null) povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/dermatologist/fire/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> fireDermatologist(@PathVariable Long idAdmina, @RequestBody DermatologistDTO zaBrisanje) {
        List<User> svi = userService.findAll();
        int okej = -1;
        if (userService.findOne(idAdmina).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(okej, HttpStatus.OK);
        okej = 2;
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(idAdmina);
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            if (dermatolog.getName().equals(zaBrisanje.getName()) && dermatolog.getSurname().equals(zaBrisanje.getSurname())) {
                if (!dermatolog.getWorking().isEmpty()) {
                    List<Examination> sviPregledi = this.examinationService.getFutureExaminations(dermatolog.getId());
                    for (Examination e : sviPregledi)
                        if (e.getAppointment().getPharmacy().getId().equals(admin.getPharmacy().getId())) {

                            okej = 1;
                            break;
                        }
                    if (sviPregledi.isEmpty() || okej != 1) {  // nema pregleda ili radi u aporeci i nema pregleda
                        Work odabrani = null;
                        for (Work w : dermatolog.getWorking()) {
                            if (w.getPharmacy().getId().equals(admin.getPharmacy().getId())) {
                                odabrani = w;
                                okej = 0;
                                break;
                            }
                        }
                        if (odabrani == null) return new ResponseEntity<>(okej, HttpStatus.OK);     // otpusten vec
                        dermatolog.getWorking().remove(odabrani);
                        this.appointmentService.deleteFreeAppointments(dermatolog.getId());
                        userService.save(dermatolog);
                        break;
                    }
                }
            }
        }
        return new ResponseEntity<>(okej, HttpStatus.OK);
    }

    @GetMapping("/getPharmacyAdmin/{idAdmina}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<PharmacyAdminDTO> getPA(@PathVariable Long idAdmina) {
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(idAdmina);
        PAdminToPAdminDTO konverter = new PAdminToPAdminDTO();
        return new ResponseEntity<>(konverter.convert(admin), HttpStatus.OK);
    }

    @GetMapping("/dermatologists/admin/{id}/{search}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
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
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    }
                    break;
                }}
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/derm/filter/admin/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
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
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    }
                    break;
                }}
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/edit/pharmacyAdmin")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> editPAdmin(@RequestBody PharmacyAdminDTO pharmacyAdminDTO){
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(pharmacyAdminDTO.getId());
        int povratna = 1;
        if (!userService.isAvaibleEmail(pharmacyAdminDTO.getEmail(), pharmacyAdminDTO.getId())) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 0;
        admin.setName(pharmacyAdminDTO.getName());
        admin.setSurname(pharmacyAdminDTO.getSurname());
        admin.setPhoneNumber(pharmacyAdminDTO.getPhoneNumber());
        admin.setEmail(pharmacyAdminDTO.getEmail());
        admin.getAddress().setState(pharmacyAdminDTO.getAddress().getState());
        admin.getAddress().setCity(pharmacyAdminDTO.getAddress().getCity());
        admin.getAddress().setStreet(pharmacyAdminDTO.getAddress().getStreet());
        admin.getAddress().setNumber(pharmacyAdminDTO.getAddress().getNumber());
        this.userService.save(admin);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/dematologist/hire/{id}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> hireDermatologist(@PathVariable Long id, @RequestBody DermaDTOHelp podaci) {
        List<User> svi = userService.findAll();
        int povratna = -1;
        if (userService.findOne(id).getClass() != PharmacyAdmin.class) return new ResponseEntity<>(povratna, HttpStatus.OK);
        // tehnicki suvisna provera ali dok ne sredimo registraciju
        PharmacyAdmin admin = (PharmacyAdmin) userService.findOne(id);
        Dermatologist izabrani = null;
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            if (dermatolog.getName().contains(podaci.getRegisterData().getName()) &&
                    dermatolog.getSurname().contains(podaci.getRegisterData().getSurname()) &&
                    dermatolog.getAddress().getState().contains(podaci.getRegisterData().getAddress().getState()) &&
                    dermatolog.getAddress().getCity().contains(podaci.getRegisterData().getAddress().getCity()) &&
                    dermatolog.getAddress().getStreet().contains(podaci.getRegisterData().getAddress().getStreet()) &&
                    dermatolog.getAddress().getNumber().contains(podaci.getRegisterData().getAddress().getNumber()) &&
                    dermatolog.getPhoneNumber().toLowerCase().contains(podaci.getRegisterData().getPhoneNumber())) {
                izabrani = dermatolog;
                break;
            }
        }
        if (izabrani == null) return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;
        LocalTime start = LocalTime.parse(podaci.getStartHour());
        LocalTime end = LocalTime.parse(podaci.getEndHour());
        if (izabrani.getWorking().isEmpty()) povratna = 0;
        for (Work w : izabrani.getWorking()) {
            if (w.getPharmacy().getId().equals(admin.getPharmacy().getId())) { povratna = -2; continue;}
            if (w.getStartHour().isBefore(start) && w.getEndHour().isBefore(start)) { povratna = 0; continue;}
            else if (w.getStartHour().isAfter(start) && w.getEndHour().isAfter(start)) { povratna = 0;  continue;}
            povratna = 1;
        }
        if (povratna != 0) return new ResponseEntity<>(povratna, HttpStatus.OK);   // ne valja vreme
        Work posao = new Work();
        posao.setPharmacy(admin.getPharmacy());
        posao.setDoctor(izabrani);
        posao.setEndHour(end);
        posao.setStartHour(start);
        izabrani.getWorking().add(posao);
        userService.save(izabrani);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/derm/filter/pharmacy/{id}")
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
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    }
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/dermatologists/filter")
    public ResponseEntity<List<DermatologistDTO>> filterAllDermatologists(@RequestBody SearchHelp podaci) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            if (dermatolog.getName().toLowerCase().contains(podaci.getSearch().toLowerCase())||
                    dermatolog.getSurname().toLowerCase().contains(podaci.getSearch().toLowerCase()) ||
                    dermatolog.getName().toLowerCase().contains(podaci.getFilterIme().toLowerCase())||
                    dermatolog.getSurname().toLowerCase().contains(podaci.getFilterPrez().toLowerCase()) ||
                    dermatolog.getAddress().getState().toLowerCase().contains(podaci.getFilterAdrD().toLowerCase()) ||
                    dermatolog.getAddress().getCity().toLowerCase().contains(podaci.getFilterAdrG().toLowerCase()) ||
                    dermatolog.getAddress().getStreet().toLowerCase().contains(podaci.getFilterAdrU().toLowerCase()) ||
                    dermatolog.getAddress().getNumber().toLowerCase().contains(podaci.getFilterAdrB().toLowerCase()) ||
                    dermatolog.getPhoneNumber().toLowerCase().contains(podaci.getFilterBroj().toLowerCase())) {
                for (Work work : dermatolog.getWorking())
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/pharmacy/{id}/{search}")
    public ResponseEntity<List<DermatologistDTO>> searchDermatologistsPharmacy(@PathVariable Long id, @PathVariable String search) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                if (work.getPharmacy().getId().equals(id)) {
                    if (dermatolog.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                            dermatolog.getName().toLowerCase().contains(search.toLowerCase())) {
                        povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    }
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/dermatologists/search/{search}")
    public ResponseEntity<List<DermatologistDTO>> searchAllDermatologists(@PathVariable String search) {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            if (dermatolog.getSurname().toLowerCase().contains(search.toLowerCase()) ||
                    dermatolog.getName().toLowerCase().contains(search.toLowerCase())) {
                for (Work work : dermatolog.getWorking())
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
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
                    povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
                    break;
                }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/allDermatologists")
    public ResponseEntity<List<DermatologistDTO>> getAllDermatologists() {
        List<User> svi = userService.findAll();
        List<DermatologistDTO> povratna = new ArrayList<>();
        for (User u : svi) if (u.getClass() == Dermatologist.class) {
            Dermatologist dermatolog = (Dermatologist) u;
            for (Work work : dermatolog.getWorking())
                povratna.add(this.dermatologistToDermatologistDTO.convert(dermatolog, work));
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("register/supplier")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> registerSupplier(@RequestBody SupplierRegistrationDTO newSupplierDto){
        if(!userService.isAvaibleEmail(newSupplierDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        Supplier newlyRegistered = new Supplier(0L, newSupplierDto.getName(), newSupplierDto.getSurname(),
                newSupplierDto.getEmail(), passwordEncoder.encode(newSupplierDto.getPassword()), newSupplierDto.getAddress(),
                newSupplierDto.getPhoneNumber(), this.userRoleService.findOne(6L), null);
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("register/dermatologist")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> registerDermatologist(@RequestBody DermatologistRegDTO newDermatologistDto){
        if(!userService.isAvaibleEmail(newDermatologistDto.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        Dermatologist newlyRegistered = new Dermatologist(0L, newDermatologistDto.getName(), newDermatologistDto.getSurname(),
                newDermatologistDto.getEmail(), passwordEncoder.encode(newDermatologistDto.getPassword()), newDermatologistDto.getAddress(),
                newDermatologistDto.getPhoneNumber(), this.userRoleService.findOne(4L), null);
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("register/systemAdmin")
    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public ResponseEntity<Boolean> registerSysAdmin(@RequestBody SysAdminRegistrationDTO newSysAdminDTO){
        if(!userService.isAvaibleEmail(newSysAdminDTO.getEmail())) return new ResponseEntity<>(false, HttpStatus.OK);
        SysAdmin newlyRegistered = new SysAdmin(0L, newSysAdminDTO.getName(), newSysAdminDTO.getSurname(),
                newSysAdminDTO.getEmail(), passwordEncoder.encode(newSysAdminDTO.getPassword()), newSysAdminDTO.getAddress(),
                newSysAdminDTO.getPhoneNumber(), this.userRoleService.findOne(1L), null);
        userService.save(newlyRegistered);
        System.out.println(newlyRegistered);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("edit/dermatologist")
    @PreAuthorize("hasAuthority('DERMATOLOGIST')")
    public ResponseEntity<Boolean> editDermatologistData(@RequestBody DermatologistDTO dermatologistDTO){
        Doctor doctor = userService.getDoctorById(dermatologistDTO.getId());
        if (doctor.getRole().getName().equals("DERMATOLOGIST")){
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
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<Boolean> editPharmacistData(@RequestBody PharmacistDTO pharmacistDTO){
        System.out.println(pharmacistDTO);
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

    @PostMapping("edit/supplier")
    public ResponseEntity<Boolean> editSupplierData(@RequestBody SupplierDTO supplierDTO){
        Supplier supplier = (Supplier) userService.findOne(supplierDTO.getId());
        supplier.setName(supplierDTO.getName());
        supplier.setSurname(supplierDTO.getSurname());
        if (supplier.getAddress() != supplierDTO.getAddress()){
            Address newAdress = Address.builder()
                    .id(null)
                    .state(supplierDTO.getAddress().getState())
                    .city(supplierDTO.getAddress().getCity())
                    .street(supplierDTO.getAddress().getStreet())
                    .number(supplierDTO.getAddress().getNumber())
                    .build();
            supplier.setAddress(newAdress);
        }
        //pharmacist.setAddress(pharmacistDTO.getAddress()); // not using this because it changes someone else's address also
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        userService.save(supplier);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/visitedDoctors/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<DoctorDTO>> getVisitedDoctors(@PathVariable Long id){
        Patient patient = (Patient) this.userService.findOne(id);
        List<Doctor> visitedDoctors = this.userService.getVisitedDoctors(patient);

        return new ResponseEntity<>(this.doctorToDoctorDTO.convert(visitedDoctors), HttpStatus.OK);
    }
}
