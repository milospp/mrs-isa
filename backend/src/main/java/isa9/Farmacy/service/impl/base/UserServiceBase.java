package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.UserRole;
import isa9.Farmacy.service.UserService;

import java.util.HashSet;
import java.util.Set;

public abstract class UserServiceBase implements UserService {
    public Set<Medicine> getPatientAllergies(User patient){
        Set<Medicine> allergies = new HashSet<>();
        if (patient.getRole() != UserRole.PATIENT) return allergies;

        return ((Patient) patient).getAllergies();

    }
}
