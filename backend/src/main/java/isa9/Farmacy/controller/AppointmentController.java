package isa9.Farmacy.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.support.DermatologistToDermatologistDTO;
import isa9.Farmacy.support.WorkToWorkDTO;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.TherapyItemDTO;
import isa9.Farmacy.service.*;
import isa9.Farmacy.support.AppointmentToAppointmentDTO;
import isa9.Farmacy.support.DateTimeDTO;
import isa9.Farmacy.support.MedicineInPharmacyToMedInPharmaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentToAppointmentDTO appointmentToAppointmentDTO;
    private final DermatologistToDermatologistDTO dermatologistToDermatologistDTO;
    private final WorkToWorkDTO workToWorkDTO;
    private final MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;
    private final MedInPharmaService medInPharmaService;
    private final PharmacyService pharmacyService;
    private final UserService userService;
    private final MedicineService medicineService;
    private final ExaminationService examinationService;
    private final WorkService workService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentToAppointmentDTO appointmentToAppointmentDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO, MedInPharmaService medInPharmaService, PharmacyService pharmacyService, UserService userService, MedicineService medicineService, ExaminationService examinationService, WorkService workService, DermatologistToDermatologistDTO dermatologistToDermatologistDTO, WorkToWorkDTO workToWorkDTO){
        this.appointmentService = appointmentService;
        this.appointmentToAppointmentDTO = appointmentToAppointmentDTO;
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
        this.medInPharmaService = medInPharmaService;
        this.pharmacyService = pharmacyService;
        this.userService = userService;
        this.medicineService = medicineService;
        this.examinationService = examinationService;
        this.workService = workService;
        this.dermatologistToDermatologistDTO = dermatologistToDermatologistDTO;
        this.workToWorkDTO = workToWorkDTO;
    }

//    @GetMapping("tmp-test")
//    public ResponseEntity<Boolean> debug(){
//        Pharmacy pharmacy = pharmacyService.findOne(1L);
//        Patient p = (Patient) userService.findOne(1L);
//        Dermatologist derma = (Dermatologist) userService.findOne(7L);
//        //Appointment a1 = new Appointment(1L, LocalDateTime.now(), 200.0, 30, TypeOfReview.EXAMINATION, derma, pharmacy, null);
//        Appointment a1 = appointmentService.findOne(1L);
//
//        Medicine m1 = medicineService.findOne(1L);
//        TherapyItem ti1 = new TherapyItem(1L, m1, 10);
//        Set<TherapyItem> therapy = new HashSet<>();
//        therapy.add(ti1);
//        Medicine m2 = medicineService.findOne(2L);
//        TherapyItem ti2 = new TherapyItem(2L, m2, 10);
//        therapy.add(ti2);
//
//        Examination e1 = new Examination(1L, p, a1, ExaminationStatus.HELD, "bolela ga je glava", "hipohondar", therapy);
//
//        a1.setExamination(e1);
//        appointmentService.save(a1);
//        p.getMyExaminations().add(e1);
//        userService.save(p);
//        return new ResponseEntity<>(true, HttpStatus.OK);
//
//    }


    @GetMapping("")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.findAll());

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<AppointmentDTO> getAnAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findOne(id);
        if (appointment == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        AppointmentDTO dto = appointmentToAppointmentDTO.convert(appointment);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("{id}/done")
    public ResponseEntity<Boolean> finishAnAppointment(@RequestBody AppointmentDTO appointmentDTO, @PathVariable Long id) {
        Appointment appointment = appointmentService.findOne(id);
        System.out.println(appointmentDTO);
        if (appointment == null) return new ResponseEntity<>(false, HttpStatus.OK);

        if (appointmentDTO == null) {
            System.out.println("Empty dto sent");
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        System.out.println(appointmentDTO.getExamination().getTherapy());

        appointment.getExamination().setExaminationInfo(appointmentDTO.getExamination().getExaminationInfo());
        appointment.getExamination().setDiagnose(appointmentDTO.getExamination().getDiagnose());
        appointment.getExamination().setStatus(appointmentDTO.getExamination().getStatus());


        Set<TherapyItem> therapy = new HashSet<>();
        for (TherapyItemDTO tiDto : appointmentDTO.getExamination().getTherapy()){
            TherapyItem ti = TherapyItem.builder()
                    .medicine(medInPharmaService.findOne(tiDto.getMedInPharma().getId()))
                    .days(tiDto.getDays())
                    .build();

            therapy.add(ti);
        }

        appointment.getExamination().setTherapy(therapy);

        appointmentService.save(appointment);

        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @PostMapping("{id}/book")
    public ResponseEntity<AppointmentDTO> bookAnAppointment(@RequestBody PatientDTO patientDTO, @PathVariable Long id) {
        if (patientDTO == null){
            // TODO: Get logged user
            System.out.println("Get logged in user");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Appointment appointment = appointmentService.bookAnAppointment(patientDTO.getId(), id);
        if (appointment == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        AppointmentDTO dto = appointmentToAppointmentDTO.convert(appointment);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("{id}/cancel")
    public ResponseEntity<AppointmentDTO> cancelAnAppointment(@PathVariable Long id) {
        // TODO: Check for user rights

        Appointment appointment = appointmentService.cancelAppointment(id);
        if (appointment == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        AppointmentDTO dto = appointmentToAppointmentDTO.convert(appointment);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @GetMapping("dermatologist/free")
    public ResponseEntity<List<AppointmentDTO>> getFreeDernatologist() {
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getAllFreeDermatologist());

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    @GetMapping("dermatologist/patient-upcoming/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPatientUpcomingDermAppointments(@PathVariable Long id) {

        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPatientUpcomingDermAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }


    @GetMapping("pharmacist/patient-upcoming/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPatientUpcomingConsultings(@PathVariable Long id) {

        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPatientUpcomingConsultingAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }


    @GetMapping("patient-upcoming/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPatientUpcomingAppointments(@PathVariable Long id) {
        this.appointmentService.getPatientUpcomingAppointments(id);
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPatientUpcomingAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @PostMapping("free-derm")
    public ResponseEntity<List<WorkDTO>> getFreeDerm(@RequestBody DermAppointmentReqDTO appointmentRequest) {

        List<WorkDTO> resultDTOS = workToWorkDTO.convert(this.appointmentService.getFreePharmacist(appointmentRequest));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @PostMapping("derm-examination")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<AppointmentDTO> bookDermAppointment(@RequestBody DermAppointmentReqDTO appointmentRequest) {

        User user = userService.getLoggedInUser();
        // TODO: CHECK IF PATIENT ALREADY HAVE APPOINTMENT AT THIS TIME

        AppointmentDTO resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.bookDermAppointment(appointmentRequest, (Patient) user));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient-past/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPastPatientAppointments(@PathVariable Long id) {

        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPastPatientAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @PostMapping("book")
    public ResponseEntity<Boolean> bookAnAppointment(@RequestBody DateTimeDTO dateTime) {
        System.out.println(dateTime);

        boolean badTime = false;

        Doctor doctor = userService.getDoctorById(dateTime.getDoctorId());
        Patient patient = userService.getPatientById(dateTime.getPatientId());
        Pharmacy pharmacy = pharmacyService.findOne(dateTime.getPharmacyId());

        boolean derm = doctor.getRole().getName().equals("DERMATOLOGIST");

        List<Appointment> patientAppointments = appointmentService.getPatientUpcomingAppointments(patient.getId());
        List<Appointment> doctorAppointments = appointmentService.getDoctorUpcomingAppointments(doctor.getId());

        List<Work> works = pharmacyService.findDoctorsWork(doctor);
        for (Work work : works){
            for (Appointment a : doctorAppointments){
                if (LocalTime.from(dateTime.getDateTime()).isAfter(work.getEndHour()) ||
                        LocalTime.from(dateTime.getDateTime()).isBefore(work.getStartHour()) ||
                        LocalTime.from(dateTime.getDateTime()).plusMinutes(dateTime.getDurationInMins()).isAfter(work.getEndHour())){
                    badTime = true;
                }
            }
        }

        // patient validation
        for (Appointment a : patientAppointments){
            if (dateTime.getDateTime().isAfter(a.getStartTime()) && dateTime.getDateTime().isBefore(a.getStartTime().plusMinutes(dateTime.getDurationInMins()))){
                badTime = true;
                break;
            }
            if (dateTime.getDateTime().isEqual(a.getStartTime()) || dateTime.getDateTime().isEqual(a.getStartTime().plusMinutes(dateTime.getDurationInMins()))){
                badTime = true;
                break;
            }
        }

        // doctor validation
        for (Appointment a : doctorAppointments){
            if (dateTime.getDateTime().isAfter(a.getStartTime()) && dateTime.getDateTime().isBefore(a.getStartTime().plusMinutes(dateTime.getDurationInMins()))){
                badTime = true;
                break;
            }
            if (dateTime.getDateTime().isEqual(a.getStartTime()) || dateTime.getDateTime().isEqual(a.getStartTime().plusMinutes(dateTime.getDurationInMins()))) {
                badTime = true;
                break;
            }
        }

        if (badTime) return new ResponseEntity<>(false, HttpStatus.OK);

        Appointment appointment = Appointment.builder()
                .id(null)
                .doctor(doctor)
                .durationInMins(dateTime.getDurationInMins())
                .examination(Examination.builder()
                        .id(null)
                        .patient(patient)
                        .status(ExaminationStatus.PENDING)
                        .build())
                .pharmacy(pharmacy)
                .price(dateTime.getPrice())
                .startTime(dateTime.getDateTime())
                .build();
        appointment.getExamination().setAppointment(appointment);
        if (derm)
            appointment.setType(TypeOfReview.EXAMINATION);
        else
            appointment.setType(TypeOfReview.COUNSELING);

        appointmentService.save(appointment);

        //AppointmentDTO dto = appointmentToAppointmentDTO.convert(appointment);
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

}

