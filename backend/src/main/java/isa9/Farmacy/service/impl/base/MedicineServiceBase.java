package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedReservationFormDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.RatingService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Random;

public abstract class MedicineServiceBase implements MedicineService {

    protected PharmacyService pharmacyService;
    protected UserService userService;
    protected RatingService ratingService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public Medicine updateMedicineRating(Medicine medicine) {
        double rating = ratingService.getMedicineAverage(medicine);
        medicine.setRating(rating);
        return save(medicine);
    }
}
