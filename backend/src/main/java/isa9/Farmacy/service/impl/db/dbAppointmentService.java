package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.ConsultingAppointmentReqDTO;
import isa9.Farmacy.repository.AppointmentRepository;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.WorkService;
import isa9.Farmacy.service.impl.base.AppointmentServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    private final WorkService workService;

    @Autowired
    public dbAppointmentService(AppointmentRepository appointmentRepository, WorkService workService) {
        this.appointmentRepository = appointmentRepository;
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
    public Set<Work> getFreePharmacist(ConsultingAppointmentReqDTO appointmentReqDTO) {
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
    public List<Appointment> getAllAppointmentsInInterval(LocalDateTime start, LocalDateTime end) {

        return appointmentRepository.getAppointmentsInInterval(start.toString(), end.toString());
    }

    @Override
    public Boolean isDermatologistFree(Long id, Long idDoktora, LocalDateTime start, int duration) {
        LocalDateTime end = start.plusMinutes(duration);
        LocalDateTime kraj;
        for (Appointment pregled : this.appointmentRepository.findAll()) {
            if (pregled.getDoctor().getId() != idDoktora || pregled.getId() == id) continue;
            kraj = pregled.getStartTime().plusMinutes(pregled.getDurationInMins());
            if ((start.isAfter(pregled.getStartTime()) && start.isBefore(kraj))
                    || (start.isBefore(pregled.getStartTime()) && end.isAfter(pregled.getStartTime()))
                    || (start.getMinute() == pregled.getStartTime().getMinute()
                        && start.getHour() == pregled.getStartTime().getHour()))
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
}
