package isa9.Farmacy.service;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.Penality;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.User;

import java.util.Set;

public interface UserService extends GenericService<User> {

    boolean isAvaibleEmail(String em);

    Set<Medicine> getPatientAllergies(User patient);
    Set<Pharmacy> getPatientSubscriptions(User patient);
    void PatientUnsubscribe(User patient, Pharmacy pharmacy);

    Set<Penality> getPenalties(User user);
    int countActivePenalties(User user);

}
