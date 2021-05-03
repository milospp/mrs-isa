package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DermAppointmentReqDTO;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AppointmentServiceBase implements AppointmentService {
    protected ExaminationService examinationService;
    protected UserService userService;
    protected PharmacyService pharmacyService;

    public AppointmentServiceBase() {
    }

    @Autowired
    public final void setExaminationService(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @Autowired
    public final void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public final void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Boolean isFreeDermAppointment(Appointment appointment) {
        if (! (appointment.getDoctor() instanceof Dermatologist)) return false;
        if (appointment.getExamination() != null) return false;

        return true;
    }

    @Override
    public Boolean isAssignedToPatient(Appointment appointment, Patient patient) {
        Examination examination;
        examination = appointment.getExamination();
        if (examination == null) return false;
        System.out.println(examination.getPatient().getName());
        if (!examination.getPatient().equals(patient)) return false;

        return true;
    }

    @Override
    public Boolean isAssignedToDoctor(Appointment appointment, Doctor doctor) {
        if (!appointment.getDoctor().equals(doctor)) return false;

        return true;
    }

    @Override
    public Boolean isDermExamination(Appointment appointment) {
        if (appointment.getType().equals(TypeOfReview.EXAMINATION)) return true;
        return false;
    }

    @Override
    public Boolean isConsulting(Appointment appointment) {
        if (appointment.getType().equals(TypeOfReview.COUNSELING)) return true;
        return false;
    }

    @Override
    public Boolean isUpcoming(Appointment appointment) {
        Examination examination;
        examination = appointment.getExamination();
        if (examination == null) return false;
        if (examination.getStatus().equals(ExaminationStatus.PENDING)) return true;
        if (examination.getStatus().equals(ExaminationStatus.CANCELED) && appointment.getStartTime().isAfter(LocalDateTime.now())) return true;

        return false;
    }

    @Override
    public Boolean isPatientValid(Patient patient) {
        int penalties = userService.countActivePenalties(patient);
        return penalties < 3;
    }

    @Override
    public Boolean isAppointmentFree(Appointment appointment) {
        if (appointment.getExamination() == null) return true;
        if (appointment.getExamination().getPatient() == null) return true;

        return false;
    }

    @Override
    public Boolean isCancelable(Appointment appointment) {
        if (isAppointmentFree(appointment)) return false;
        if (appointment.getStartTime().minusDays(1).isBefore(LocalDateTime.now())) return false;
        if (appointment.getExamination().getStatus() == ExaminationStatus.CANCELED) return false;

        return true;
    }

    @Override
    public List<Appointment> getAllFreeDermatologist() {
        List<Appointment> allAppointments;
        allAppointments = findAll().stream().filter(x -> isFreeDermAppointment(x)).collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public Boolean isCanceled(Appointment appointment) {
        if (appointment.getExamination() == null) return false;
        if (appointment.getExamination().getStatus().equals(ExaminationStatus.CANCELED)) return true;
        return false;
    }

    @Override
    public List<Appointment> getPatientUpcomingDermAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        if (!user.getRole().getName().equals("PATIENT")) return new ArrayList<>();
        Patient patient = (Patient) user;

        List<Appointment> allAppointments = findAll();
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && isDermExamination(x) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPatientUpcomingConsultingAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        if (!user.getRole().getName().equals("PATIENT")) return new ArrayList<>();
        Patient patient = (Patient) user;

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && isConsulting(x) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPatientUpcomingAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        System.out.println(user.getRole());
        System.out.println(user.getRole());
        System.out.println(user.getRole());
        if (!user.getRole().getName().equals("PATIENT")) return new ArrayList<>();
        Patient patient = (Patient) user;

        List<Appointment> allAppointments = findAll();
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPastPatientAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        System.out.println(user.getName());
        if (!user.getRole().getName().equals("PATIENT")) return new ArrayList<>();
        Patient patient = (Patient) user;
        System.out.println(patient.getName());

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && !isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public Appointment bookAnAppointment(Long patientId, Long appointmentId) {
        User user = userService.findOne(patientId);
        if (!user.getRole().getName().equals("PATIENT")) return null;
        Patient patient = (Patient) user;
        if (!isPatientValid(patient)) return null;

        Appointment appointment = findOne(appointmentId);

        if (!isAppointmentFree(appointment)) return null;


        Examination examination = Examination.builder()
                .patient(patient)
                .status(ExaminationStatus.NOT_HELD)
                .appointment(appointment)
                .therapy(new HashSet<>())
                .build();
        appointment.setExamination(examination);
        save(appointment);

        return appointment;
    }

    @Override
    public Appointment cloneAppointment(Appointment appointment) {
        Appointment cloned = Appointment.builder()
                .startTime(appointment.getStartTime())
                .price(appointment.getPrice())
                .durationInMins(appointment.getDurationInMins())
                .type(appointment.getType())
                .doctor(appointment.getDoctor())
                .pharmacy(appointment.getPharmacy())
                .examination(appointment.getExamination())
                .examination(null)
                .build();

        return cloned;
    }

    @Override
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = findOne(appointmentId);
        if (appointment == null) return null;
        if (!isCancelable(appointment)) return null;

        appointment.getExamination().setStatus(ExaminationStatus.CANCELED);

        save(appointment);
        if (appointment.getType().equals(TypeOfReview.EXAMINATION))
            save(cloneAppointment(appointment));
        return appointment;
    }

    @Override
    public List<Pharmacist> getOccupiedPharmacists(LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointments = getAllAppointmentsInInterval(start, end);
        Set<Pharmacist> pharmacists = new HashSet<Pharmacist>();

        for (Appointment appointment : appointments){
            if (appointment.getDoctor().getRole().getName().equalsIgnoreCase("PHARMACIST") && !isCanceled(appointment)){
                pharmacists.add((Pharmacist) appointment.getDoctor());
            }
        }
        List<Pharmacist> listPharmacist = new ArrayList<>();
        listPharmacist.addAll(pharmacists);
        return listPharmacist;
    }

    @Override
    public Appointment bookDermAppointment(DermAppointmentReqDTO appointmentReqDTO, Patient patient) {

        Set<Work> workSet = getFreePharmacist(appointmentReqDTO);
        Boolean available = workSet.stream().anyMatch(w ->
                w.getDoctor().getId() == appointmentReqDTO.getPharmacistId() &&
                w.getPharmacy().getId() == appointmentReqDTO.getPharmacyId());

        if (!available) return null;

        Doctor doctor = userService.getDoctorById(appointmentReqDTO.getPharmacistId());
        Pharmacy pharmacy = pharmacyService.findOne(appointmentReqDTO.getPharmacyId());

        // TODO: Fix price
        Double price = 444.0;

        Appointment appointment = Appointment.builder()
                .doctor(doctor).pharmacy(pharmacy)
                .durationInMins(appointmentReqDTO.getDurationInMins())
                .startTime(appointmentReqDTO.getStartTime())
                .price(price * appointmentReqDTO.getDurationInMins() / 60)
                .type(TypeOfReview.COUNSELING)
                .build();

        Examination examination = Examination.builder()
                .patient(patient)
                .status(ExaminationStatus.PENDING)
                .appointment(appointment).build();
        appointment.setExamination(examination);

        return save(appointment);




    @Override
    public List<Appointment> getDoctorUpcomingAppointments(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, doctor) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }
}