package isa9.Farmacy.service;

import isa9.Farmacy.model.Vacation;

import java.util.List;

public interface VacationService extends GenericService<Vacation>{
    List<Vacation> getAllForPharmacy(Long pharmacyId);
    List<Vacation> getAllForPharmacyAdmin(Long pharmacyAdminId);
    List<Vacation> getAllForDoctor(Long doctorId);

    boolean testTime(Vacation vacation);
}
