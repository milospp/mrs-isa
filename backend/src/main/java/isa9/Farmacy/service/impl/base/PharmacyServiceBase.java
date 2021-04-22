package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PharmacyServiceBase implements PharmacyService {
    protected RatingService ratingService;

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public boolean pharmacyExists(Pharmacy p) {
        for(Pharmacy ph : this.findAll()){
            if(ph.getName().equals(p.getName()) && ph.getAddress().equals(p.getAddress())) return true;
        };

        return false;
    }

    @Override
    public boolean reduceQuantity(Pharmacy pharmacy, Medicine medicine, int resQuantity) {
        MedicineInPharmacy mip = pharmacy.getMedicines().stream().filter(m -> m.getMedicine().equals(medicine)).findFirst().get();
        int quantity = mip.getInStock();
        if (quantity < resQuantity) return false;

        quantity -= resQuantity;
        mip.setInStock(quantity);

        save(pharmacy);

        return true;
    }

    @Override
    public MedicineInPharmacy gedMedicineInPharmacy(Pharmacy pharmacy, Medicine medicine) {
        //TODO: check if null!!
        return pharmacy.getMedicines().stream().filter(x -> x.getMedicine().getId().equals(medicine.getId())).findFirst().get();
    }

    @Override
    public Pharmacy updatePharmacyRating(Pharmacy pharmacy) {
        double rating = ratingService.getPharmacyAverage(pharmacy);
        pharmacy.setRating(rating);
        return save(pharmacy);
    }


}