package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class RatingServiceBase implements RatingService {
    protected UserService userService;
    protected AppointmentService appointmentService;
    protected MedicineService medicineService;
    protected MedReservationService medReservationService;
    protected PharmacyService pharmacyService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Autowired
    public void setMedicineService(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Autowired
    public void setMedReservationService(MedReservationService medReservationService) {
        this.medReservationService = medReservationService;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }


    @Override
    public Boolean isRateValid(int rating) {
        if (rating < 1) return false;
        if (rating > 5) return false;
        return true;
    }

    @Override
    public double getDoctorAverage(Doctor doctor) {
        return getDoctorAverage(doctor.getId());
    }



    @Override
    public Rating getPatientDoctorRate(Long patientId, Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        Patient patient = userService.getPatientById(patientId);
        if (doctor == null || patient == null) return null;

        return getPatientDoctorRate(patient, doctor);
    }

    @Override
    public Rating rateDoctor(Long doctorId, Long userId, int rate) {
        Doctor doctor = userService.getDoctorById(doctorId);
        Patient patient = userService.getPatientById(userId);
        if (!isRateValid(rate)) return null;
        if (doctor == null || patient == null) return null;

        Rating rating = getPatientDoctorRate(patient, doctor);

        if (appointmentService.patientCanRateDoctor(patient,doctor)){
            rating.setRating(rate);
            Rating ret = save(rating);

            userService.updateDoctorRating(doctor);
            return ret;
        }

        return null;
    }

    @Override
    public double getMedicineAverage(Medicine medicine) {
        return getMedicineAverage(medicine.getId());
    }

    @Override
    public Rating getPatientMedicineRate(Long patientId, Long medicineId) {
        Medicine medicine = medicineService.findOne(medicineId);
        Patient patient = userService.getPatientById(patientId);
        if (medicine == null || patient == null) return null;

        return getPatientMedicineRate(patient, medicine);
    }

    @Override
    public Rating rateMedicine(Long medicineId, Long userId, int rate) {
        Medicine medicine = medicineService.findOne(medicineId);
        Patient patient = userService.getPatientById(userId);
        if (!isRateValid(rate)) return null;
        if (medicine == null || patient == null) return null;

        Rating rating = getPatientMedicineRate(patient, medicine);

        if (medReservationService.patientConsumedMedicine(patient,medicine)){
            rating.setRating(rate);
            Rating ret = save(rating);

            medicineService.updateMedicineRating(medicine);
            return ret;
        }

        return null;
    }


    // Pharmacy rate
    @Override
    public double getPharmacyAverage(Pharmacy pharmacy) {
        return getPharmacyAverage(pharmacy.getId());
    }

    @Override
    public Rating getPatientPharmacyRate(Long patientId, Long pharmacyId) {
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        Patient patient = userService.getPatientById(patientId);
        if (pharmacy == null || patient == null) return null;

        return getPatientPharmacyRate(patient, pharmacy);
    }

    @Override
    public Rating ratePharmacy(Long pharmacyId, Long userId, int rate) {
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        Patient patient = userService.getPatientById(userId);
        if (!isRateValid(rate)) return null;
        if (pharmacy == null || patient == null) return null;

        Rating rating = getPatientPharmacyRate(patient, pharmacy);

        if (medReservationService.patientConsumedMedInPharmacy(patient,pharmacy) || appointmentService.patientHadAppointmentInPharmacy(patient, pharmacy)){
            rating.setRating(rate);
            Rating ret = save(rating);

            pharmacyService.updatePharmacyRating(pharmacy);
            return ret;
        }

        return null;
    }

    @Override
    public Boolean canUserRate(Long patientId, Long pharmacyId) {
        Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
        Patient patient = userService.getPatientById(patientId);
        if (pharmacy == null || patient == null) return null;

        Rating rating = getPatientPharmacyRate(patient, pharmacy);

        if (medReservationService.patientConsumedMedInPharmacy(patient,pharmacy) || appointmentService.patientHadAppointmentInPharmacy(patient, pharmacy)){
            return true;
        }
        return false;
    }
}