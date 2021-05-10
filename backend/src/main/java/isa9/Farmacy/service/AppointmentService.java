package isa9.Farmacy.service;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.AppointmentSearchDTO;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    Boolean isCanceled(Appointment appointment);
    Boolean isInPharmacy(Appointment appointment, Long pharmacyId);

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

    List<Pharmacist> getOccupiedPharmacists(LocalDateTime start, LocalDateTime end);
    Set<Work> getFreePharmacist(ConsultingAppointmentReqDTO appointmentReqDTO);
    List<Appointment> getAllAppointmentsInInterval(LocalDateTime start, LocalDateTime end);

    Appointment bookConsultingAppointment(ConsultingAppointmentReqDTO appointmentReqDTO, Patient patient);
    List<Appointment> getDoctorUpcomingAppointments(Long id);

    List<Appointment> filterPastAppointments(List<Appointment> appointments, AppointmentSearchDTO appointmentSearchDTO);

    List<Appointment> getDermForPharmacyAppointments(Long dermId, Long pharamcyId);
    Boolean isDermatologistFree(Long id, Long idDoktora, LocalDateTime start, int duration);
    void deleteApponitment(Long id);
    int canEditDelete(Long id);

    Boolean isPatientOccupied(LocalDateTime start, LocalDateTime end, Long patientId);
}
