package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.DoctorRepository;
import isa9.Farmacy.repository.InquiryMedRepository;
import isa9.Farmacy.repository.PharmacyRepository;
import isa9.Farmacy.repository.WorkRepository;
import isa9.Farmacy.service.InquiryService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.PharmacyServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Service
public class dbPharmacyService extends PharmacyServiceBase implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final WorkRepository workRepository;
    private final InquiryService inquiryService;

    @Autowired
    public dbPharmacyService(PharmacyRepository pharmacyRepository, WorkRepository workRepository, InquiryService inquiryService) {
        this.pharmacyRepository = pharmacyRepository;
        this.workRepository = workRepository;
        this.inquiryService = inquiryService;
    }

    @Override
    public List<Pharmacy> findAll() {
        List<Pharmacy> povratna = new ArrayList<>();
        for (Pharmacy ap : this.pharmacyRepository.findAll()) {
            ap.setInquiryMedicines(this.inquiryService.getAllForPharmacy(ap.getId()));
        }
        return this.pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy findOne(Long id) {
        Pharmacy apoteka = this.pharmacyRepository.findById(id).orElse(null);
        if (apoteka != null)
            apoteka.setInquiryMedicines(this.inquiryService.getAllForPharmacy(apoteka.getId()));
        return apoteka;
    }

    @Override
    public Pharmacy save(Pharmacy entity) { return this.pharmacyRepository.save(entity); }


    @Override
    public List<Work> findDoctorsWork(Doctor doctor) {
        return workRepository.findAllByDoctor(doctor);
    }

    @Override
    public boolean pharmacyExists(Pharmacy p) {
        List<Pharmacy> allPharmacies = this.findAll();

        for(Pharmacy ph : allPharmacies){
            if(p.equals(ph)) return true;
        }

        return false;
    }
}
