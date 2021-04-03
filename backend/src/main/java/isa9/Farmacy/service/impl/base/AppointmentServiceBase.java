package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AppointmentServiceBase implements AppointmentService {
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
}