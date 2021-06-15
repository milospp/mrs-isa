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
import isa9.Farmacy.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@Service
public class dbPharmacyService extends PharmacyServiceBase implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final WorkRepository workRepository;
    private final InquiryService inquiryService;
    private final MailService mailService;

    @Autowired
    public dbPharmacyService(PharmacyRepository pharmacyRepository, WorkRepository workRepository,
            InquiryService inquiryService, MailService mailService) {
        this.pharmacyRepository = pharmacyRepository;
        this.workRepository = workRepository;
        this.inquiryService = inquiryService;
        this.mailService = mailService;
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
        if (id == null) return null;
        Pharmacy apoteka = this.pharmacyRepository.findById(id).orElse(null);
        if (apoteka != null)
            apoteka.setInquiryMedicines(this.inquiryService.getAllForPharmacy(apoteka.getId()));
        return apoteka;
    }

    @Override
    public Pharmacy save(Pharmacy entity) { return this.pharmacyRepository.save(entity); }


    @Override
    @Transactional
    public Pharmacy findOneLocked(Long id) {
        return pharmacyRepository.findPharmacyByIdLocked(id);
    }

    @Override
    public List<Work> findDoctorsWork(Doctor doctor) {
        return workRepository.findAllByDoctor(doctor);
    }

    @Override
    public void checkMedicineInPharmacy(Pharmacy pharmacy, List<MedicineQuantity> medicines) {
        boolean nadjen;
        for (MedicineQuantity mq : medicines) {
            nadjen = false;
            for (MedicineInPharmacy uApoteci : pharmacy.getMedicines()) {
                System.out.println(mq.getMedicine().getName());
                System.out.println(uApoteci.getMedicine().getName());
                if (mq.getMedicine().getId().equals( uApoteci.getMedicine().getId() )) {
                    nadjen = true;
                    break;
                }

            }
            if (!nadjen) {
                MedicineInPharmacy noviLek = new MedicineInPharmacy();
                noviLek.setInStock(0);
                MedPrice cena = new MedPrice();
                cena.setMedicineInPharmacy(noviLek);
                cena.setStartDate(LocalDateTime.now());
                cena.setPriceType(PriceType.NORMAL);
                cena.setPrice(77.0);
                noviLek.setCurrentPrice(cena);
                noviLek.setPharmacy(pharmacy);
                noviLek.setMedicine(mq.getMedicine());
                pharmacy.getMedicines().add(noviLek);
                this.medInPharmaService.save(noviLek);
                save(pharmacy);
            }
        }
    }

    @Override
    public void sendActionMail(MedPrice actionPromotion, Boolean delete) {
        Pharmacy apoteka = actionPromotion.getMedicineInPharmacy().getPharmacy();
        for (Patient pacijent : this.userService.howSucribePharmacy(apoteka.getId()))
          this.mailService.sendActionInfo(actionPromotion, pacijent, delete);
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
