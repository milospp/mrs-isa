package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.MedInPharmaDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UserServiceBase implements UserService {
    protected MedicineService medicineService;
    protected MedReservationService medReservationService;
    protected PharmacyService pharmacyService;
    protected RatingService ratingService;
    protected VerificationTokenService verificationTokenService;

    @Override
    public boolean isPatientBlocked(Patient patient) {
        if (countActivePenalties(patient) >= 3) return true;
        return false;
    }

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
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
    public void setVerificationTokenService(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public Set<Medicine> getPatientAllergies(User patient) {
        if (!patient.getRole().getName().equals("PATIENT")) return new HashSet<>();

        return ((Patient) patient).getAllergies();

    }

    @Override
    public Set<Pharmacy> getPatientSubscriptions(User patient) {
        if (!patient.getRole().getName().equals("PATIENT")) return new HashSet<>();

        return ((Patient) patient).getSubscriptions();
    }

    @Override
    public void PatientUnsubscribe(User user, Pharmacy pharmacy) {
        if (!user.getRole().getName().equals("PATIENT")) return;

        Patient patient = (Patient) user;
        Set<Pharmacy> subscriptions = ((Patient) patient).getSubscriptions();
        subscriptions.remove(pharmacy);
        patient.setSubscriptions(subscriptions);
        save(patient);
        return;

    }

    @Override
    public int countActivePenalties(User user) {
        if (!user.getRole().getName().equals("PATIENT")) return 0;
        Patient patient = (Patient) user;

        int penalties = 0;

        for (Penality penality : ((Patient) user).getPenalties()) {
            if (penality.getDate().plusMonths(1).isAfter(LocalDate.now()))
                ++penalties;
        }

        return penalties;
    }

    @Override
    public Set<Penality> getPenalties(User user) {
        if (!user.getRole().getName().equals("PATIENT")) return new HashSet<>();

        return ((Patient) user).getPenalties();
    }

    @Override
    public Patient updatePatient(PatientDTO patientDTO) {
        Patient patient = (Patient) findOne(patientDTO.getId());

        if (patientDTO.getName() != null) {
            patient.setName(patientDTO.getName());
        }
        if (patientDTO.getSurname() != null) {
            patient.setSurname(patientDTO.getSurname());
        }
        if (patientDTO.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientDTO.getPhoneNumber());
        }
        if (patientDTO.getAddress() != null) {
            patient.setAddress(patientDTO.getAddress());
        }

        save(patient);
        return patient;
    }

    @Override
    public Set<Medicine> getPatientAllergies(Long patientId) {
        User user = findOne(patientId);

        if (!user.getRole().getName().equals("PATIENT")) return new HashSet<>();

        return ((Patient) user).getAllergies();
    }

    @Override
    public Medicine addPatientAllergy(Patient patient, Long medicineId) {
        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) return null;

        patient.getAllergies().add(medicine);
        save(patient);
        return medicine;
    }

    @Override
    public Medicine removePatientAllergy(Patient patient, Long medicineId) {
        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) return null;

        patient.getAllergies().remove(medicine);
        save(patient);
        return medicine;
    }

    @Override
    public Set<MedReservation> getPatientReservations(Long patientId) {
        User user = findOne(patientId);
        if (!user.getRole().getName().equals("PATIENT")) return new HashSet<>();
        Patient patient = (Patient) user;

        return patient.getReservations();

    }

    @Override
    public Doctor updateDoctorRating(Doctor doctor) {
        double rating = ratingService.getDoctorAverage(doctor);
        doctor.setRating(rating);
        return (Doctor) save(doctor);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;

//        User user = (User) authentication.getPrincipal();
        User user = getByEmail(authentication.getName());
        return user;
    }

    @Override
    public boolean changePassword(Long id, String newPassword) {
        User user = this.findOne(id);
        if(user == null) return false;

        user.setPassword(newPassword);
        user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
        this.save(user);
        return true;
    }

    @Override
    public Penality addPenalty(Patient patient, String reason) {
        Penality penality = new Penality();
        penality.setDate(LocalDate.now());
        penality.setReason(reason);
        patient.getPenalties().add(penality);

        save(patient);
        return penality;
    }

    @Override
    public List<Doctor> getVisitedDoctors(Patient patient) {
        List<Doctor> visitedDoctors = new ArrayList<>();

        for(Examination e : patient.getMyExaminations()){
            if(e.getStatus().equals(ExaminationStatus.HELD)){
                visitedDoctors.add(e.getAppointment().getDoctor());
            }
        }

        return visitedDoctors;
    }

    //0 - everthing is ok; 1 - token expired; 2 - token not found in database
    @Override
    public Integer activateUser(String token) {
        VerificationToken vtoken = this.verificationTokenService.findByToken(token);

        if(vtoken.isExpired()) return 1;
        if(vtoken == null) return 2;

        Patient patient = vtoken.getPatient();

        patient.setEnabled(true);

        this.save(patient);

        return 0;
    }
}