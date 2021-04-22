package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.*;
import isa9.Farmacy.repository.*;
import isa9.Farmacy.service.RatingService;
import isa9.Farmacy.service.impl.base.RatingServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbRatingService extends RatingServiceBase implements RatingService {

    private RatingRepository ratingRepository;
    private RatingDoctorRepository ratingDoctorRepository;
    private RatingMedicineRepository ratingMedicineRepository;
    private RatingPharmacyRepository ratingPharmacyRepository;

    @Autowired
    public dbRatingService(RatingRepository ratingRepository, RatingDoctorRepository ratingDoctorRepository, RatingMedicineRepository ratingMedicineRepository, RatingPharmacyRepository ratingPharmacyRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingDoctorRepository = ratingDoctorRepository;
        this.ratingMedicineRepository = ratingMedicineRepository;
        this.ratingPharmacyRepository = ratingPharmacyRepository;
    }

    @Override
    public List<Rating> findAll() {
        return this.ratingRepository.findAll();
    }

    @Override
    public Rating findOne(Long id) {
        return this.ratingRepository.findById(id).orElse(null);
    }

    @Override
    public Rating save(Rating entity) { return this.ratingRepository.save(entity); }

    @Override
    public RatingMedicine findOneMedicineR(Long id) {
        return this.ratingMedicineRepository.findById(id).orElse(null);
    }

    @Override
    public RatingPharmacy findOnePharmacyR(Long id) {
        return this.ratingPharmacyRepository.findById(id).orElse(null);
    }

    @Override
    public RatingDoctor findOneDoctorR(Long id) {
        return this.ratingDoctorRepository.findById(id).orElse(null);
    }

    @Override
    public List<RatingMedicine> findAllMedicineR() {
        return this.ratingMedicineRepository.findAll();
    }

    @Override
    public List<RatingPharmacy> findAllPharmacyR() {
        return this.ratingPharmacyRepository.findAll();
    }

    @Override
    public List<RatingDoctor> findAllDoctorR() {
        return this.ratingDoctorRepository.findAll();
    }


    @Override
    public double getDoctorAverage(Long doctorId) {
        Double val = this.ratingDoctorRepository.getAverageDoctorRating(doctorId);
        if (val == null) return 0;
        return (double) val;
    }

    @Override
    public Rating getPatientDoctorRate(Patient patient, Doctor doctor) {
        Rating rating;
        rating = ratingDoctorRepository.findByUserAndAndDoctor(patient, doctor);
        if (rating == null) {
            rating = new RatingDoctor(null, patient, -1, doctor);
        }
        return rating;
    }

    @Override
    public double getMedicineAverage(Long medicineId) {
        Double val = this.ratingMedicineRepository.getAverageMedicineRating(medicineId);
        if (val == null) return 0;
        return (double) val;    }

    @Override
    public Rating getPatientMedicineRate(Patient patient, Medicine medicine) {
        Rating rating;
        rating = ratingMedicineRepository.findFirstByUserAndMedicine(patient, medicine);
        if (rating == null) {
            rating = new RatingMedicine(null, patient, -1, medicine);
        }
        return rating;
    }

    @Override
    public double getPharmacyAverage(Long pharmacyId) {
        Double val = this.ratingPharmacyRepository.getAveragePharmacyRating(pharmacyId);
        if (val == null) return 0;
        return (double) val;
    }

    @Override
    public Rating getPatientPharmacyRate(Patient patient, Pharmacy pharmacy) {
        Rating rating;
        rating = ratingPharmacyRepository.findFirstByUserAndPharmacy(patient, pharmacy);
        if (rating == null) {
            rating = new RatingPharmacy(null, patient, -1, pharmacy);
        }
        return rating;
    }
}
