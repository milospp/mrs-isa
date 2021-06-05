package isa9.Farmacy.service;

import isa9.Farmacy.model.Vacation;
import isa9.Farmacy.utils.MailService;

import java.time.LocalDate;
import java.util.List;

public interface VacationService extends GenericService<Vacation>{
    List<Vacation> getAllForPharmacy(Long pharmacyId);
    List<Vacation> getAllForAdmin(Long adminId);
    List<Vacation> getAllForDoctor(Long doctorId);
    List<Vacation> getWaitnigAll();

    boolean testTime(Vacation vacation);
    boolean checkDate(Long idDoktora, LocalDate datum);
    void cancelAppointments(Vacation vacation, MailService mailService);
}
