package isa9.Farmacy.service;

import isa9.Farmacy.model.*;

import java.util.List;

public interface RatingService extends GenericService<Rating> {

    RatingMedicine findOneMedicineR(Long id);
    RatingPharmacy findOnePharmacyR(Long id);
    RatingDoctor findOneDoctorR(Long id);

    List<RatingMedicine> findAllMedicineR();
    List<RatingPharmacy> findAllPharmacyR();
    List<RatingDoctor> findAllDoctorR();

    double getDoctorAverage(Doctor doctor);
    double getDoctorAverage(Long doctorId);

    Rating getPatientDoctorRate(Patient patient, Doctor doctor);
    Rating rateDoctor(Long doctorId, Long userId, int rating);

}
