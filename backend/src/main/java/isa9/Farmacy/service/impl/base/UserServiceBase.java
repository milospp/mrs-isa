package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class UserServiceBase implements UserService {
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
}
