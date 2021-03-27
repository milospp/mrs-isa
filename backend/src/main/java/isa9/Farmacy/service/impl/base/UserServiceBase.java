package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.UserService;

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
}
