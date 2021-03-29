package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Dermatologist;
import isa9.Farmacy.service.AppointmentService;

import java.util.Objects;

public abstract class AppointmentServiceBase implements AppointmentService {
    @Override
    public Boolean isFreeDermAppointment(Appointment appointment) {
        if (! (appointment.getDoctor() instanceof Dermatologist)) return false;
        if (appointment.getExamination() != null) return false;

        return true;
    }
}