package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.MedInPharmaRepository;
import isa9.Farmacy.repository.MedicineRepository;
import isa9.Farmacy.service.MedInPharmaService;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.impl.base.MedInPharmaServiceBase;
import isa9.Farmacy.service.impl.base.MedicineServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@Primary
@Service
public class dbMedInPharmacyService extends MedInPharmaServiceBase implements MedInPharmaService {
    private final MedInPharmaRepository medInPharmaRepository;

    @Autowired
    public dbMedInPharmacyService(MedInPharmaRepository medInPharmaRepository) {
        this.medInPharmaRepository = medInPharmaRepository;
    }

    @Override
    public List<MedicineInPharmacy> findAll() {
        return this.medInPharmaRepository.findAll();
    }

    @Override
    public MedicineInPharmacy findOne(Long id) {
        return this.medInPharmaRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineInPharmacy save(MedicineInPharmacy entity) { return this.medInPharmaRepository.save(entity); }

    @Override
    public Collection<MedicineInPharmacy> findAllMedicinesInPharmacy(Long medicineId) {
        return medInPharmaRepository.findAllByMedicineId(medicineId);
    }

    @Override
    @Scheduled(cron="0 0 0 * * ?") // proveri svakog dana u ponoc
    public void checkForExpiredActionOrPromotion() {
        for (Pharmacy apoteka : this.pharmacyService.findAll()) {
            for (MedicineInPharmacy lekUApoteci : apoteka.getMedicines()) {
                MedPrice trenutnaCena = lekUApoteci.getCurrentPrice();
                if (trenutnaCena.getPriceType() == PriceType.NORMAL) continue;
                if (trenutnaCena.getEndDate().isAfter(LocalDateTime.now())) continue;
                MedPrice novaCena = new MedPrice();
                novaCena.setMedicineInPharmacy(trenutnaCena.getMedicineInPharmacy());
                novaCena.setPriceType(PriceType.NORMAL);
                novaCena.setStartDate(LocalDateTime.now());
                novaCena.setPrice(trenutnaCena.getOldPrice());
                lekUApoteci.setCurrentPrice(novaCena);
                save(lekUApoteci);
            }
        }
    }
}
