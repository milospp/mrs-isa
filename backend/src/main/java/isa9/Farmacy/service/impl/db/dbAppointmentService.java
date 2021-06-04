package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;
import isa9.Farmacy.repository.AppointmentRepository;
import isa9.Farmacy.repository.ExaminationRepository;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.WorkService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
@Service
public class dbAppointmentService extends AppointmentServiceBase implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ExaminationRepository examinationRepository;
    private final WorkService workService;

    @Autowired
    public dbAppointmentService(AppointmentRepository appointmentRepository, ExaminationRepository examinationRepository, WorkService workService) {
        this.appointmentRepository = appointmentRepository;
        this.examinationRepository = examinationRepository;
        this.workService = workService;
    }

    @Override
    public List<Appointment> findAll() {
        return this.appointmentRepository.findAll();
    }

    @Override
    public Appointment findOne(Long id) {
        return this.appointmentRepository.findById(id).orElseGet(null);
    }

    @Override
    public Appointment save(Appointment entity) {
        if (entity.getExamination() != null) {this.examinationService.save(entity.getExamination());System.out.println("Nananna");}
        return this.appointmentRepository.save(entity);
    }


    @Override
    public Boolean patientCanRateDoctor(Patient patient, Doctor doctor) {
        Appointment appointment;
        appointment = appointmentRepository.findFirstByDoctorAndExamination_PatientAndExamination_Status(doctor,patient,ExaminationStatus.HELD);
        if (appointment == null) return false;
        else return true;
    }

    @Override
    public Boolean patientHadAppointmentInPharmacy(Patient patient, Pharmacy pharmacy) {
        Appointment appointment;
        appointment = appointmentRepository.findFirstByPharmacyAndExamination_PatientAndExamination_Status(pharmacy,patient,ExaminationStatus.HELD);
        if (appointment == null) return false;
        else return true;
    }

    @Override
    @Transactional
    //@Transactional(propagation = Propagation.REQUIRED)
    public Set<Work> getFreePharmacist(ConsultingAppointmentReqDTO appointmentReqDTO) {
        if (appointmentReqDTO.getStartTime() == null) return new HashSet<>();
        if (appointmentReqDTO.getStartTime().isBefore(LocalDateTime.now())) return new HashSet<>();

        LocalDateTime start = appointmentReqDTO.getStartTime();
        LocalDateTime end = appointmentReqDTO.getStartTime().plusMinutes(appointmentReqDTO.getDurationInMins());
        if (start.until(end, ChronoUnit.DAYS) >= 1 ) return new HashSet<>();

        List<Pharmacist> dermatologists = getOccupiedPharmacists(start, end);
        List<Work> employees = workService.getWorksInInterval(start.toLocalTime(), end.toLocalTime());
        Set<Work> workSet = new HashSet<>(employees);
        Set<Work> invalidWorkSet = new HashSet<>();

        for (Work work : workSet) {
            if (work.getDoctor().getRole().getId() != 5L) invalidWorkSet.add(work);
            else if (dermatologists.stream().anyMatch(d -> d.getId() == work.getDoctor().getId())) invalidWorkSet.add(work);
        }

        workSet.removeAll(invalidWorkSet);

        return workSet;

    }

    @Override
    @Transactional
    //@Transactional(propagation = Propagation.REQUIRED)
    public List<Appointment> getAllAppointmentsInInterval(LocalDateTime start, LocalDateTime end) {

        return appointmentRepository.getAppointmentsInInterval(start.toString(), end.toString());
    }

    @Override
    @Transactional
    public Appointment bookConsultingAppointment(ConsultingAppointmentReqDTO appointmentReqDTO, Patient patient) {

        patient = userService.getPatientByIdLocked(patient.getId());
        Doctor doctor = userService.getDoctorByIdLocked(appointmentReqDTO.getPharmacistId());

        if (userService.isPatientBlocked(patient)) return null;
        if (appointmentReqDTO.getDurationInMins() <= 0) return null;
        if (appointmentReqDTO.getStartTime().isBefore(LocalDateTime.now())) return null;
        if (this.isPatientOccupied(
                appointmentReqDTO.getStartTime(),
                appointmentReqDTO.getStartTime().plusMinutes(appointmentReqDTO.getDurationInMins()),
                patient.getId()
        )) return null;


        Set<Work> workSet = getFreePharmacist(appointmentReqDTO);
        Boolean available = workSet.stream().anyMatch(w ->
                w.getDoctor().getId() == appointmentReqDTO.getPharmacistId() &&
                        w.getPharmacy().getId() == appointmentReqDTO.getPharmacyId());

        if (!available) return null;

        Pharmacy pharmacy = pharmacyService.findOne(appointmentReqDTO.getPharmacyId());

        Double price = pharmacy.getPricePerHour();
        if (price == null) price = 999.0;

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
                .therapy(new HashSet<>())
                .appointment(appointment).build();
        appointment.setExamination(examination);

        save(appointment);
        mailService.sendAppointmentInfo(appointment, false);

        return appointment;
    }

    @Override
    public Boolean isDermatologistFree(Long id, Long idDoktora, LocalDateTime start, int duration) {
        LocalDateTime end = start.plusMinutes(duration);
        LocalDateTime kraj;
        for (Appointment pregled : this.appointmentRepository.findAll()) {
            if (pregled.getDoctor().getId() != idDoktora || pregled.getId() == id) continue;
            kraj = pregled.getStartTime().plusMinutes(pregled.getDurationInMins());
            if ((start.isAfter(pregled.getStartTime()) && start.isBefore(kraj))
                    || (start.isBefore(pregled.getStartTime()) && end.isAfter(pregled.getStartTime())))
                return false;
        }
        return true;
    }

    @Override
    public void deleteApponitment(Long id) {
        List<Examination> zaBrisanje = new ArrayList<>();
        for (User u : this.userService.findAll()) {
            if (u.getClass() == Patient.class) {
                zaBrisanje.clear();
                for (Examination e : ((Patient) u).getMyExaminations()) {
                    if (e.getAppointment().getId() == id) {
                        zaBrisanje.add(e);
                    }
                }
                ((Patient) u).getMyExaminations().removeAll(zaBrisanje);
                this.userService.save(u);
            }
        }

        for (Examination ex : this.examinationService.findAll()) {
            if (ex.getAppointment().getId() == id) {
                this.examinationService.delete(ex);}
        }
        this.appointmentRepository.delete(this.appointmentRepository.getOne(id));
    }

    @Override
    public int canEditDelete(Long id) {
        for (Examination ex : this.examinationService.findAll())
            if (ex.getAppointment().getId() == id) {
                if (ex.getStatus() != ExaminationStatus.CANCELED) continue;
                if (ex.getStatus() == ExaminationStatus.HELD || ex.getStatus() == ExaminationStatus.NOT_HELD)
                    return 1;               // bio je u proslosti
                else return 2;              // neko je rezervisao
            }
        return 0;                           // mozes da menjas
    }


    @Override
    public Appointment findByStartTime(LocalDateTime last) {
        return appointmentRepository.findByStartTime(last);
    }
}
