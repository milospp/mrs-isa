package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.service.MedicineService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class UserServiceBase implements UserService {
    protected MedicineService medicineService;

    @Autowired
    public void setMedicineService(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Override
    public Set<Medicine> getPatientAllergies(User patient){
        if (patient.getRole() != UserRole.PATIENT) return new HashSet<>();

        return ((Patient) patient).getAllergies();

    }

    @Override
    public Set<Pharmacy> getPatientSubscriptions(User patient) {
        if (patient.getRole() != UserRole.PATIENT) return new HashSet<>();

        return ((Patient) patient).getSubscriptions();
    }

    @Override
    public void PatientUnsubscribe(User user, Pharmacy pharmacy) {
        if (user.getRole() != UserRole.PATIENT) return;

        Patient patient = (Patient) user;
        Set<Pharmacy> subscriptions = ((Patient) patient).getSubscriptions();
        subscriptions.remove(pharmacy);
        patient.setSubscriptions(subscriptions);

        return;

    }

    @Override
    public int countActivePenalties(User user) {
        if (user.getRole() != UserRole.PATIENT) return 0;
        Patient patient = (Patient) user;

        int penalties = 0;

        for (Penality penality : ((Patient) user).getPenalties()){
            if (penality.getDate().plusMonths(1).isAfter(LocalDate.now()))
                ++penalties;
        }

        return penalties;
    }

    @Override
    public Set<Penality> getPenalties(User user) {
        if (user.getRole() != UserRole.PATIENT) return new HashSet<>();

        return ((Patient) user).getPenalties();
    }

    @Override
    public Patient updatePatient(PatientDTO patientDTO) {
        Patient patient = (Patient) findOne(patientDTO.getId());

        if (patientDTO.getName() != null){
            patient.setName(patientDTO.getName());
        }
        if (patientDTO.getSurname() != null){
            patient.setSurname(patientDTO.getSurname());
        }
        if (patientDTO.getPhoneNumber() != null){
            patient.setPhoneNumber(patientDTO.getPhoneNumber());
        }
        if (patientDTO.getAddress() != null){
            patient.setAddress(patientDTO.getAddress());
        }

        save(patient);
        return patient;
    }

    @Override
    public Set<Medicine> getPatientAllergies(Long patientId) {
        User user = findOne(patientId);

        if (user.getRole() != UserRole.PATIENT) return new HashSet<>();

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
}
