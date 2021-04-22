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

    Boolean isRateValid(int rating);

    double getDoctorAverage(Doctor doctor);
    double getDoctorAverage(Long doctorId);

    Rating getPatientDoctorRate(Patient patient, Doctor doctor);
    Rating getPatientDoctorRate(Long patientId, Long doctorId);
    Rating rateDoctor(Long doctorId, Long userId, int rating);


    double getMedicineAverage(Medicine medicine);
    double getMedicineAverage(Long medicineId);

    Rating getPatientMedicineRate(Patient patient, Medicine medicine);
    Rating getPatientMedicineRate(Long patientId, Long medicineId);
    Rating rateMedicine(Long medicineId, Long userId, int rating);


    double getPharmacyAverage(Pharmacy pharmacy);
    double getPharmacyAverage(Long pharmacyId);

    Rating getPatientPharmacyRate(Patient patient, Pharmacy pharmacy);
    Rating getPatientPharmacyRate(Long patientId, Long pharmacyId);
    Rating ratePharmacy(Long pharmacyId, Long userId, int rating);

    Boolean canUserRate(Long patientId, Long pharmacyId);
}
