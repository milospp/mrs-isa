package isa9.Farmacy.service;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Patient;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService extends GenericService<Appointment>{

    Boolean isFreeDermAppointment(Appointment appointment);
    Boolean isAssignedToPatient(Appointment appointment, Patient patient);
    Boolean isAssignedToDoctor(Appointment appointment, Doctor doctor);
    Boolean isDermExamination(Appointment appointment);
    Boolean isConsulting(Appointment appointment);
    Boolean isUpcoming(Appointment appointment);


    List<Appointment> getAllFreeDermatologist();

    List<Appointment> getPatientUpcomingDermAppointments(Long patientId);
    List<Appointment> getPatientUpcomingConsultingAppointments(Long patientId);
    List<Appointment> getPatientUpcomingAppointments(Long patientId);

    List<Appointment> getPastPatientAppointments(Long patientId);
}
