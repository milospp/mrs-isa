package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.service.MedReservationService;
import isa9.Farmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class MedReservationServiceBase implements MedReservationService {
    protected PharmacyService pharmacyService;

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Boolean isCancelable(MedReservation medReservation) {
        if (medReservation.isCanceled()) return false;
        if (medReservation.isTaken()) return false;

        LocalDate lastDate = medReservation.getLastDate();
        LocalDate limitDate = lastDate.minusDays(1);
        if (limitDate.isBefore(LocalDate.now())) return false;


        return true;
    }

    @Override
    public MedReservation cancel(Long medReservationId) {
        MedReservation medReservation = findOne(medReservationId);
        Boolean cancelable = isCancelable(medReservation);
        if (!cancelable) return medReservation;

        medReservation.setCanceled(true);
        MedicineInPharmacy mip = medReservation.getMedicineInPharmacy();
        Pharmacy pharmacy = mip.getPharmacy();
        mip.setInStock(mip.getInStock() + medReservation.getQuantity());
        pharmacyService.save(pharmacy);
        save(medReservation);

        return medReservation;
    }
}