package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.AppointmentSearchDTO;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;
import isa9.Farmacy.service.*;
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AppointmentServiceBase implements AppointmentService {
    protected ExaminationService examinationService;
    protected UserService userService;
    protected PharmacyService pharmacyService;
    protected MailService mailService;
    protected WorkService workService;

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

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public final void setWorkService(WorkService workService) {
        this.workService = workService;
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
        if (examination.getPatient() == null) return false;
        if (!examination.getPatient().getId().equals(patient.getId())) return false;

        return true;
    }

    @Override
    public Boolean isAssignedToDoctor(Appointment appointment, Doctor doctor) {
        if (!appointment.getDoctor().getId().equals(doctor.getId())) return false;

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
        List<Appointment> allAppointments = new ArrayList<>();
        for (Appointment a :  findAll())
            if (a.getDoctor().getClass() == Dermatologist.class && a.getExamination() == null
                    && a.getStartTime().isAfter(LocalDateTime.now()))
                allAppointments.add(a);
        return allAppointments;
    }

    @Override
    public Boolean isCanceled(Appointment appointment) {
        if (appointment.getExamination() == null) return false;
        if (appointment.getExamination().getStatus().equals(ExaminationStatus.CANCELED)) return true;
        return false;
    }

    @Override
    public Boolean isInPharmacy(Appointment appointment, Long pharmacyId) {
        if (appointment.getPharmacy().getId().equals(pharmacyId))
            return true;
        else
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
    @Transactional
    public Appointment bookAnAppointment(Long patientId, Long appointmentId) {
        User user = userService.findOne(patientId);
        if (user == null || !user.getRole().getName().equals("PATIENT")) return null;
        Patient patient = (Patient) user;
        if (userService.isPatientBlocked(patient)) return null;

        Appointment appointment = findOne(appointmentId);

        if (!isAppointmentFree(appointment)) return null;


        Examination examination = Examination.builder()
                .patient(patient)
                .status(ExaminationStatus.PENDING)
                .appointment(appointment)
                .therapy(new HashSet<>())
                .build();
        appointment.setExamination(examination);

        save(appointment);
        mailService.sendAppointmentInfo(appointment, false);

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
    @Transactional
    //@Transactional(propagation = Propagation.REQUIRED)
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
    public List<Appointment> getDoctorUpcomingAppointments(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);

        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, doctor) && isUpcoming(x))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> filterPastAppointments(List<Appointment> appointments, AppointmentSearchDTO appointmentSearchDTO) {
        if (appointmentSearchDTO == null) return appointments;
        final Map<String, Comparator<Appointment>> critMap = new HashMap<String, Comparator<Appointment>>();

        critMap.put("DATE_ASC", Comparator.comparing(Appointment::getStartTime).reversed());
        critMap.put("DATE_DES", Comparator.comparing(Appointment::getStartTime));
        critMap.put("DOCTOR_ASC", (o1,o2)->{return (o1.getDoctor().getName() + " " + o1.getDoctor().getSurname()).compareToIgnoreCase(o2.getDoctor().getName() + " " + o2.getDoctor().getSurname()); });
        critMap.put("DOCTOR_DES", (o2,o1)->{return (o1.getDoctor().getName() + " " + o1.getDoctor().getSurname()).compareToIgnoreCase(o2.getDoctor().getName() + " " + o2.getDoctor().getSurname()); });
        critMap.put("PHARMACY_ASC", (o1,o2)->{return o1.getPharmacy().getName().trim().compareToIgnoreCase(o2.getPharmacy().getName().trim());});
        critMap.put("PHARMACY_DES", (o2,o1)->{return o1.getPharmacy().getName().trim().compareToIgnoreCase(o2.getPharmacy().getName().trim());});
        critMap.put("STATUS_ASC", (o1,o2)->{return o1.getExamination().getStatus().toString().compareToIgnoreCase(o2.getExamination().getStatus().toString());});
        critMap.put("STATUS_DES", (o2,o1)->{return o1.getExamination().getStatus().toString().compareToIgnoreCase(o2.getExamination().getStatus().toString());});
        critMap.put("DURATION_ASC", Comparator.comparingDouble(Appointment::getDurationInMins));
        critMap.put("DURATION_DES", Comparator.comparingDouble(Appointment::getDurationInMins).reversed());
        critMap.put("PRICE_ASC", Comparator.comparingDouble(Appointment::getPrice));
        critMap.put("PRICE_DES", Comparator.comparingDouble(Appointment::getPrice).reversed());

        Comparator<Appointment> comp = critMap.getOrDefault(appointmentSearchDTO.getSort().toUpperCase().trim(), critMap.values().iterator().next());
        // TODO : FIX IF EXAMINATION IS NULL
        return appointments.stream()
                .filter(a -> appointmentSearchDTO.getDoctorName().isEmpty() || (a.getDoctor().getName() + " " + a.getDoctor().getSurname()).toLowerCase().contains(appointmentSearchDTO.getDoctorName().toLowerCase()))
                .filter(a -> appointmentSearchDTO.getPharmacyName().isEmpty() || a.getPharmacy().getName().toLowerCase().contains(appointmentSearchDTO.getPharmacyName()))
                .filter(a -> appointmentSearchDTO.getStatus().isEmpty() || a.getPharmacy().getName().toLowerCase().contains(appointmentSearchDTO.getPharmacyName()))
                .filter(a -> appointmentSearchDTO.getStatus().equalsIgnoreCase("ALL") || a.getExamination().getStatus().toString().equalsIgnoreCase(appointmentSearchDTO.getStatus()))
                .filter(a -> appointmentSearchDTO.getStartTime() == null || a.getStartTime().isAfter(appointmentSearchDTO.getStartTime()))
                .filter(a -> appointmentSearchDTO.getEndTime() == null || a.getStartTime().isBefore(appointmentSearchDTO.getEndTime()))
                .filter(a -> a.getDurationInMins() >= appointmentSearchDTO.getMinDuration() && a.getDurationInMins() <= appointmentSearchDTO.getMaxDuration())
                .filter(a -> a.getPrice() >= appointmentSearchDTO.getMinPrice() && a.getPrice() <= appointmentSearchDTO.getMaxPrice())
                .sorted(comp).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getDermForPharmacyAppointmentsNotCanceled(Long dermId, Long pharamcyId) {
        Doctor d = userService.getDoctorById(dermId);
        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, d) && isInPharmacy(x, pharamcyId) && !isCanceled(x))
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getDoctorAppointmentsNotCanceled(Long doctorId) {
        Doctor d = userService.getDoctorById(doctorId);
        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, d) && !isCanceled(x))
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getDermForPharmacyAppointmentsFree(Long dermId, Long pharamcyId) {
        Doctor d = userService.getDoctorById(dermId);
        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, d) && isInPharmacy(x, pharamcyId) && x.getExamination() == null)
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getDoctorAppointmentsFree(Long doctorId) {
        Doctor d = userService.getDoctorById(doctorId);
        List<Appointment> allAppointments;
        allAppointments = findAll().stream()
                .filter(x -> isAssignedToDoctor(x, d) && x.getExamination() == null)
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .collect(Collectors.toList());
        return allAppointments;
    }

    @Override
    public List<Appointment> getPatientDoctorNotCanceledAppointments(Long patientId, Long doctorId) {
        Doctor d = userService.getDoctorById(doctorId);
        Patient p = userService.getPatientById(patientId);
        List<Appointment> appointments = findAll().stream().filter(x -> isAssignedToDoctor(x, d) && isAssignedToPatient(x, p) && !isCanceled(x)).sorted(Comparator.comparing(Appointment::getStartTime)).collect(Collectors.toList());
        return appointments;
    }

    @Override
    public List<User> getDoctorNotCanceledAppointmentsPatients(Long doctorId) {
        Doctor d = userService.getDoctorById(doctorId);
        List<User> patients = userService.findAll().stream().filter(x -> !getPatientDoctorNotCanceledAppointments(x.getId(), d.getId()).isEmpty()).collect(Collectors.toList());
        return patients;
    }

    @Override
    public Appointment getPatientDoctorNotCanceledAppointmentLast(Long patientId, Long doctorId) {
        Doctor d = userService.getDoctorById(doctorId);
        Patient p = userService.getPatientById(patientId);
        Appointment appointments = findAll().stream().filter(x -> isAssignedToDoctor(x, d) && isAssignedToPatient(x, p) && !isCanceled(x)).sorted(Comparator.comparing(Appointment::getStartTime).reversed()).collect(Collectors.toList()).get(0);
        return appointments;
    }

    @Override
    @Transactional
    public Boolean isPatientOccupied(LocalDateTime start, LocalDateTime end, Long patientId) {
        Collection<Appointment> appointments = this.getAllAppointmentsInInterval(start, end);
        for (Appointment appointment : appointments){
            if (appointment.getExamination() == null) continue;
            if (appointment.getExamination().getPatient().getId().equals( patientId )) return true;
        }
        return false;

    }

    @Override
    @Transactional // TODO Isidora did this...
    public Boolean isDoctorOccupied(LocalDateTime start, LocalDateTime end, Long doctorId) {
        List<Appointment> appointments = getAllAppointmentsInInterval(start, end);
        for (Appointment appointment : appointments){
            if (appointment.getExamination() == null) continue;
            if (appointment.getDoctor().getId().equals(doctorId)) {
                return true;
            }
        }
        return false;
    }
}