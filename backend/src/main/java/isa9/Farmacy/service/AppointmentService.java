package isa9.Farmacy.service;

import isa9.Farmacy.model.Appointment;

import java.util.List;

public interface AppointmentService extends GenericService<Appointment>{

    List<Appointment> getAllFreeDermatologist();
    Boolean isFreeDermAppointment(Appointment appointment);
}
