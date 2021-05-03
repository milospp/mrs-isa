package isa9.Farmacy.service;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DermAppointmentReqDTO;
import isa9.Farmacy.model.dto.DermatologistRegDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService extends GenericService<Appointment>{

    Boolean isFreeDermAppointment(Appointment appointment);
    Boolean isAssignedToPatient(Appointment appointment, Patient patient);
    Boolean isAssignedToDoctor(Appointment appointment, Doctor doctor);
    Boolean isDermExamination(Appointment appointment);
    Boolean isConsulting(Appointment appointment);
    Boolean isUpcoming(Appointment appointment);
    Boolean isAppointmentFree(Appointment appointment);
    Boolean isCancelable(Appointment appointment);
    Boolean isPatientValid(Patient patient);

    Appointment bookAnAppointment(Long patientId, Long appointmentId);
    Appointment cancelAppointment(Long appointmentId);
    Appointment cloneAppointment(Appointment appointment);


    List<Appointment> getAllFreeDermatologist();

    List<Appointment> getPatientUpcomingDermAppointments(Long patientId);
    List<Appointment> getPatientUpcomingConsultingAppointments(Long patientId);
    List<Appointment> getPatientUpcomingAppointments(Long patientId);

    List<Appointment> getPastPatientAppointments(Long patientId);

    Boolean patientCanRateDoctor(Patient patient, Doctor doctor);
    Boolean patientHadAppointmentInPharmacy(Patient patient, Pharmacy pharmacy);

    List<Dermatologist> getFreeDermatologist(DermAppointmentReqDTO appointmentReqDTO);
    List<Appointment> getAllAppointmentsInInterval(LocalDateTime start, LocalDateTime end);
}
