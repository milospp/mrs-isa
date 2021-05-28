package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.PharmacyAdmin;
import isa9.Farmacy.model.Vacation;
import isa9.Farmacy.repository.VacationRepository;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.VacationService;
import isa9.Farmacy.service.impl.base.VacationServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbVacationService extends VacationServiceBase implements VacationService {

    private final VacationRepository vacationRepository;
    private final PharmacyService pharmacyService;
    private final UserService userService;

    @Autowired
    public dbVacationService(VacationRepository vacationRepository, PharmacyService pharmacyService, UserService userService) {
        this.vacationRepository = vacationRepository;
        this.pharmacyService = pharmacyService;
        this.userService = userService;
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
    public List<Vacation> getAllForPharmacyAdmin(Long pharmacyAdminId) {
        PharmacyAdmin pharmacyAdmin = userService.findPharmacyAdmin(pharmacyAdminId);
        return vacationRepository.findByPharmacyAdmin(pharmacyAdmin);
    }

    @Override
    public List<Vacation> getAllForDoctor(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return vacationRepository.findByDoctor(doctor);
    }
}
