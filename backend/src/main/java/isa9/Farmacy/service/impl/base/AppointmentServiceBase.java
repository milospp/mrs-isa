package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.ExaminationService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if (examination.getStatus().equals(ExaminationStatus.NOT_HELD)) return true;
        if (examination.getStatus().equals(ExaminationStatus.CANCELED) && appointment.getStartTime().isAfter(LocalDateTime.now())) return true;

        return false;
    }

    @Override
    public List<Appointment> getAllFreeDermatologist() {
        List<Appointment> allAppointments;
        allAppointments = findAll().stream().filter(x -> isFreeDermAppointment(x)).collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPatientUpcomingDermAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        if (!user.getRole().equals(UserRole.PATIENT)) return new ArrayList<>();
        Patient patient = (Patient) user;

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && isDermExamination(x) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPatientUpcomingConsultingAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        if (!user.getRole().equals(UserRole.PATIENT)) return new ArrayList<>();
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
        if (!user.getRole().equals(UserRole.PATIENT)) return new ArrayList<>();
        Patient patient = (Patient) user;

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPastPatientAppointments(Long patientId) {
        User user = userService.findOne(patientId);
        System.out.println(user.getName());
        if (!user.getRole().equals(UserRole.PATIENT)) return new ArrayList<>();
        Patient patient = (Patient) user;
        System.out.println(patient.getName());

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToPatient(x, patient) && !isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }
}