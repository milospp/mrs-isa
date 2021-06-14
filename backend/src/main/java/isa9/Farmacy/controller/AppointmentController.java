package isa9.Farmacy.controller;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.*;
import isa9.Farmacy.support.*;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.TherapyItemDTO;
import isa9.Farmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
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
    private final AppointmentToAppointmentCalendarDTO appointmentToAppointmentCalendarDTO;
    private final VacationService vacationService;
    private final LoyaltyProgramService loyaltyProgramService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentToAppointmentDTO appointmentToAppointmentDTO, MedicineInPharmacyToMedInPharmaDTO medicineInPharmacyToMedInPharmaDTO, MedInPharmaService medInPharmaService, PharmacyService pharmacyService, UserService userService, MedicineService medicineService, ExaminationService examinationService, WorkService workService, DermatologistToDermatologistDTO dermatologistToDermatologistDTO, WorkToWorkDTO workToWorkDTO, AppointmentToAppointmentCalendarDTO appointmentToAppointmentCalendarDTO, VacationService vacationService, LoyaltyProgramService loyaltyProgramService){
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
        this.appointmentToAppointmentCalendarDTO = appointmentToAppointmentCalendarDTO;
        this.vacationService = vacationService;
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @GetMapping("")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.findAll());

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<AppointmentDTO> getAnAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findOne(id);
        if (appointment == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        AppointmentDTO dto = appointmentToAppointmentDTO.convert(appointment);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("{id}/done")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<Boolean> finishAnAppointment(@RequestBody AppointmentDTO appointmentDTO, @PathVariable Long id) {
        Appointment appointment = appointmentService.findOne(id);

        if (appointment == null) return new ResponseEntity<>(false, HttpStatus.OK);

        if (appointmentDTO == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

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
        Patient patient = appointment.getExamination().getPatient();
        patient.setPoints(patient.getPoints() + this.loyaltyProgramService.getExaminationPointsReward());
        this.userService.save(patient);
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

    @PreAuthorize("hasAuthority('PATIENT')")
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
    public ResponseEntity<List<WorkDTO>> getFreeDerm(@RequestBody ConsultingAppointmentReqDTO appointmentRequest) {

        List<WorkDTO> resultDTOS = workToWorkDTO.convert(this.appointmentService.getFreePharmacist(appointmentRequest));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @PostMapping("derm-examination")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<AppointmentDTO> bookDermAppointment(@RequestBody ConsultingAppointmentReqDTO appointmentRequest) {

        User user = userService.getLoggedInUser();
        // TODO: CHECK IF PATIENT ALREADY HAVE APPOINTMENT AT THIS TIME

        Appointment appointment = this.appointmentService.bookConsultingAppointment(appointmentRequest, (Patient) user);
        if (appointment == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        AppointmentDTO resultDTOS = appointmentToAppointmentDTO.convert(appointment);

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient-past/{id}")
    public ResponseEntity<List<AppointmentDTO>> getPastPatientAppointments(@PathVariable Long id) {

        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.getPastPatientAppointments(id));

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @PostMapping("patient-past/{id}/search")
    public ResponseEntity<List<AppointmentDTO>> searchPastPatientAppointments(@PathVariable Long id, @RequestBody AppointmentSearchDTO appointmentSearchDTO) {

        List<Appointment> appointments = this.appointmentService.getPastPatientAppointments(id);
        appointments = appointmentService.filterPastAppointments(appointments, appointmentSearchDTO);
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(appointments);
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }

    @PostMapping("book")
    @PreAuthorize("hasAuthority('DERMATOLOGIST') or hasAuthority('PHARMACIST')")
    public ResponseEntity<Boolean> bookAnAppointment(@RequestBody DateTimeDTO dateTime) {
        boolean valid = appointmentService.bookFromAppointment(dateTime);

        Doctor doctor = userService.getDoctorById(dateTime.getDoctorId());
        Patient patient = userService.getPatientById(dateTime.getPatientId());
        Pharmacy pharmacy = pharmacyService.findOne(dateTime.getPharmacyId());

        LocalDateTime tryBookDateTime = dateTime.getDateTime();
        LocalDateTime tryBookDateTimeEnd = tryBookDateTime.plusMinutes(dateTime.getDurationInMins());

        boolean badTime = vacationService.getIfAcceptedInIntervalForDoctor(dateTime.getDoctorId(),
                tryBookDateTime.toLocalDate(), tryBookDateTimeEnd.toLocalDate());

        if (badTime) {
            //System.out.println("Tad ima odmor");
            valid = false;
        }


        return new ResponseEntity<>(valid, HttpStatus.OK);
    }

    @GetMapping("calendar/derm/{dermId}/pharmacy/{pharmaId}")
    @PreAuthorize("hasAuthority('DERMATOLOGIST')")
    public ResponseEntity<List<AppointmentCalendarDTO>> getDermaPharmaAppointmentsForCalendar(@PathVariable Long dermId, @PathVariable Long pharmaId) {
        List<Appointment> appointments = this.appointmentService.getDermForPharmacyAppointmentsNotCanceled(dermId, pharmaId);

        List<AppointmentCalendarDTO> resultDTOs = appointmentToAppointmentCalendarDTO.convert(appointments);

        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }

    @GetMapping("calendar/free-derm/{dermId}/pharmacy/{pharmaId}")
    @PreAuthorize("hasAuthority('DERMATOLOGIST')")
    public ResponseEntity<List<AppointmentCalendarDTO>> getFreeDermaPharmaAppointmentsForCalendar(@PathVariable Long dermId, @PathVariable Long pharmaId) {
        List<Appointment> appointments = this.appointmentService.getDermForPharmacyAppointmentsFree(dermId, pharmaId);

        List<AppointmentCalendarDTO> resultDTOs = appointmentToAppointmentCalendarDTO.convert(appointments);

        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }

    @GetMapping("calendar/pharm/{pharmId}")
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<List<AppointmentCalendarDTO>> getPharmaAppointmentsForCalendar(@PathVariable Long pharmId) {
        List<Appointment> appointments = this.appointmentService.getDoctorAppointmentsNotCanceled(pharmId);

        List<AppointmentCalendarDTO> resultDTOs = appointmentToAppointmentCalendarDTO.convert(appointments);

        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }

    @GetMapping("calendar/free-pharm/{pharmId}")
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<List<AppointmentCalendarDTO>> getFreePharmaAppointmentsForCalendar(@PathVariable Long pharmId) {
        List<Appointment> appointments = this.appointmentService.getDoctorAppointmentsFree(pharmId);

        List<AppointmentCalendarDTO> resultDTOs = appointmentToAppointmentCalendarDTO.convert(appointments);

        return new ResponseEntity<>(resultDTOs, HttpStatus.OK);
    }
  
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> pharmacyAdminMake(@RequestBody AppointmentDTO podaci) {
        int povratna = this.proveriVreme(podaci);
        if (povratna != 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        if (!this.vacationService.checkDate(podaci.getDoctor().getId(), podaci.getStartTime().toLocalDate()))
            return new ResponseEntity<>(povratna, HttpStatus.OK);
        povratna = 1;
        podaci.setType(TypeOfReview.EXAMINATION);
        AppointmentDTOtoAppointment konverter = new AppointmentDTOtoAppointment(this.userService, this.pharmacyService);
        Appointment pregled = konverter.convert(podaci);
        this.appointmentService.save(pregled);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @GetMapping("/allForPharmacy/{idApoteke}")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAppAdmin(@PathVariable Long idApoteke) {
        List<AppointmentDTO> povratna = new ArrayList<>();
        for (Appointment pregled : this.appointmentService.findAll()) {
            if (pregled.getDoctor().getClass() == Pharmacist.class) continue;
            if (idApoteke.equals(pregled.getPharmacy().getId())) {
                for (Work w : pregled.getDoctor().getWorking())
                    if (w.getPharmacy().getId().equals( pregled.getPharmacy().getId())) {
                        AppointmentDTO pregledDTO = this.appointmentToAppointmentDTO.convert(pregled, w);
                        pregledDTO.setCanEdit(this.appointmentService.canEditDelete(pregled.getId()));
                        povratna.add(pregledDTO);
                        break;
                    }
            }
        }
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> deleteAppointmentAdmin(@RequestBody AppointmentDTO pregled) {
        int povratna = -1;
        Appointment odabrani = this.appointmentService.findOne(pregled.getId());
        if (odabrani == null) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        povratna = this.appointmentService.canEditDelete(pregled.getId()) ? 0 : 1;
        if (povratna != 0) return new ResponseEntity<>(povratna, HttpStatus.OK);
        this.appointmentService.deleteApponitment(pregled.getId());
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Integer> editAppointmentAdmin(@RequestBody AppointmentDTO pregled) {
        int povratna = -1;
        Appointment odabrani = this.appointmentService.findOne(pregled.getId());
        if (odabrani == null) return new ResponseEntity<>(povratna, HttpStatus.NOT_FOUND);
        povratna = 1;
        if (pregled.getStartTime().isBefore(LocalDateTime.now())) return new ResponseEntity<>(povratna, HttpStatus.OK);

        povratna = this.proveriVreme(pregled);
        if (povratna != 0) new ResponseEntity<>(povratna, HttpStatus.OK);
        odabrani.setStartTime(pregled.getStartTime());
        odabrani.setDurationInMins(pregled.getDurationInMins());
        odabrani.setPrice(pregled.getPrice());
        this.appointmentService.save(odabrani);
        return new ResponseEntity<>(povratna, HttpStatus.OK);
    }


    private int proveriVreme(AppointmentDTO podaci) {
        LocalTime pocetakPregleda = LocalTime.of(podaci.getStartTime().getHour(), podaci.getStartTime().getMinute());
        if (podaci.getDoctor().getPharmacyWork().getStartHour().isAfter(pocetakPregleda) ||
                podaci.getDoctor().getPharmacyWork().getEndHour().isBefore(pocetakPregleda))
            return -1;  // radno vreme nije tad = -1

        LocalTime krajPregleda = LocalTime.of(podaci.getStartTime().getHour(), podaci.getStartTime().getMinute());
        krajPregleda = krajPregleda.plusMinutes(podaci.getDurationInMins());
        if (podaci.getDoctor().getPharmacyWork().getEndHour().isBefore(krajPregleda))
            return -2;  // probija mu radno vreme = -2

        if (!this.appointmentService.isDermatologistFree(podaci.getId(), podaci.getDoctor().getId(), podaci.getStartTime(), podaci.getDurationInMins()))
            return -3; // preklapa se sa nekim drugim terminom = -3

        return 0;      // vreme je okej
    }
}

