package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.VacationRepository;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.VacationService;
import isa9.Farmacy.service.impl.base.VacationServiceBase;
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Service
public class dbVacationService extends VacationServiceBase implements VacationService {

    private final VacationRepository vacationRepository;
    private final PharmacyService pharmacyService;
    private final UserService userService;
    private final AppointmentService appointmentService;

    @Autowired
    public dbVacationService(VacationRepository vacationRepository, PharmacyService pharmacyService, UserService userService, AppointmentService appointmentService) {
        this.vacationRepository = vacationRepository;
        this.pharmacyService = pharmacyService;
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @Override
    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    @Override
    public Vacation findOne(Long id) {
        return vacationRepository.findById(id).orElse(null);
    }

    @Override
    public Vacation save(Vacation entity) {
        return vacationRepository.save(entity);
    }

    @Override
    public List<Vacation> getAllForPharmacy(Long pharmacyId) {
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        return vacationRepository.findByPharmacy(pharmacy);
    }

    @Override
    public List<Vacation> getAllForAdmin(Long adminId) {
        PharmacyAdmin pharmacyAdmin = userService.findPharmacyAdmin(adminId);
        return vacationRepository.findByAdmin(pharmacyAdmin);
    }

    @Override
    public List<Vacation> getAllForDoctor(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return vacationRepository.findByDoctor(doctor);
    }

    @Override
    public List<Vacation> getAcceptedForDoctor(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return vacationRepository.findByDoctor(doctor).stream()
                .filter(x -> x.getStatus().equals(VacationRequestStatus.ACCEPTED))
                .collect(Collectors.toList());
    }

    @Override
    public boolean getIfAcceptedInIntervalForDoctor(Long doctorId, LocalDate start, LocalDate end) {
        List<Vacation> vacations = getAcceptedForDoctor(doctorId);
        for (Vacation v : vacations){
            if (v.getStartDate().isAfter(start) && v.getStartDate().isAfter(end)){
                continue;
            }
            else if (v.getEndDate().isBefore(start) && v.getEndDate().isBefore(end)){
                continue;
            }
            else{
                return true;
            }
        }
        return false;
    }
  
    @Override
    public List<Vacation> getWaitnigAll() {
        List<Vacation> povratna = new ArrayList<>();
        for (Vacation odmor : findAll()) {
            if (odmor.getStatus() == VacationRequestStatus.WAITING)
                povratna.add(odmor);
        }
        return povratna;
    }

    @Override
    public boolean testTime(Vacation vacation) {
        // checking for appointments...
        List<Appointment> allApps = appointmentService.getDoctorAppointmentsNotCanceled(vacation.getDoctor().getId());
        for (Appointment a : allApps) {
            if (a.getExamination() != null) { // if there is an examination
                if (a.getExamination().getStatus() == ExaminationStatus.PENDING) { // if it has not been held yet, too early
                    if (a.getPharmacy().getId().equals(vacation.getPharmacy().getId()) && // if it is in the same pharmacy
                            a.getStartTime().isAfter(vacation.getStartDate().atStartOfDay()) && // if it is in between dates
                            a.getStartTime().isBefore(vacation.getEndDate().atStartOfDay())) {
                        return false;
                    }
                }
            }
        }
        // other accepted checking vacations...
        List<Vacation> allVacs = getAllForDoctor(vacation.getDoctor().getId());
        for (Vacation v : allVacs) {
            if (v.getStatus() == VacationRequestStatus.ACCEPTED && v.getStartDate().isAfter(vacation.getStartDate()) && v.getStartDate().isBefore(vacation.getEndDate())){
                return false;
            }
            if (v.getStatus() == VacationRequestStatus.ACCEPTED && v.getEndDate().isAfter(vacation.getStartDate()) && v.getEndDate().isBefore(vacation.getEndDate())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkDate(Long idDoktora, LocalDate datum) {
        for (Vacation v : findAll()) {
            if (v.getDoctor().getId() != idDoktora) continue;
            if (v.getStatus() == VacationRequestStatus.DENIED || v.getStatus() == VacationRequestStatus.WAITING)
                continue;

            // dobar doktor i prihvacen godisnji
            if ((v.getStartDate().isBefore(datum) && v.getEndDate().isAfter(datum)) ||
                v.getStartDate().isEqual(datum) || v.getEndDate().isEqual(datum))
                return false;
        }
        return true;
    }

    @Override
    public void cancelAppointments(Vacation vacation, MailService mailService) {
        List<Appointment> pregledi = appointmentService.getDoctorAppointmentsNotCanceled(vacation.getDoctor().getId());
        for (Appointment p : pregledi) {                                                        // za svaki pregled
            if (p.getExamination() != null) {                                                   // ako ima zakzan
                if (p.getExamination().getStatus() == ExaminationStatus.PENDING) {              // ako je na cekanju
                    if (p.getPharmacy().getId().equals(vacation.getPharmacy().getId()) &&       // u istoj apoteci
                            p.getStartTime().isAfter(vacation.getStartDate().atStartOfDay()) && // vreme se podudara
                            p.getStartTime().isBefore(vacation.getEndDate().atStartOfDay())) {
                        p.getExamination().setStatus(ExaminationStatus.CANCELED);               // otkazi ga
                        this.appointmentService.save(p);
                        mailService.sendAppointmentInfo(p, true);                       // posalji mejl
                    }
                }
            }
            else {
                Examination prazan = new Examination();
                prazan.setAppointment(p);
                prazan.setStatus(ExaminationStatus.CANCELED);
                prazan.setPatient(null);
                prazan.setTherapy(new HashSet<>());
                p.setExamination(prazan);
                this.appointmentService.save(p);
            }
        }
    }
}
