package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.TherapyItemDTO;
import isa9.Farmacy.service.*;
import isa9.Farmacy.support.AppointmentToAppointmentDTO;
import isa9.Farmacy.support.MedicineInPharmacyToMedInPharmaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentToAppointmentDTO appointmentToAppointmentDTO;
    private final MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO;
    private final MedInPharmaService medInPharmaService;
    private final PharmacyService pharmacyService;
    private final UserService userService;
    private final MedicineService medicineService;
    private final ExaminationService examinationService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentToAppointmentDTO appointmentToAppointmentDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO, MedInPharmaService medInPharmaService, PharmacyService pharmacyService, UserService userService, MedicineService medicineService, ExaminationService examinationService){
        this.appointmentService = appointmentService;
        this.appointmentToAppointmentDTO = appointmentToAppointmentDTO;
        this.medicineInPharmacyToMedInPharmaDTO = medicineInPharmacyToMedInPharmaDTO;
        this.medInPharmaService = medInPharmaService;
        this.pharmacyService = pharmacyService;
        this.userService = userService;
        this.medicineService = medicineService;
        this.examinationService = examinationService;
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

    @GetMapping("patient-past/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPastPatientAppointments(@PathVariable Long id) {

        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPastPatientAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

}

