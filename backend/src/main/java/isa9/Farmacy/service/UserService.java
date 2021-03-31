package isa9.Farmacy.service;

import isa9.Farmacy.model.*;

import java.util.Set;

public interface UserService extends GenericService<User> {

    boolean isAvaibleEmail(String em);

    Set<Medicine> getPatientAllergies(User patient);
    Set<Pharmacy> getPatientSubscriptions(User patient);
    void PatientUnsubscribe(User patient, Pharmacy pharmacy);

    Set<Penality> getPenalties(User user);
    int countActivePenalties(User user);

    PharmacyAdmin findPharmacyAdmin(Long pharmacyId);

    //Pharmacy findPharmacistPharmacy(Long id);
}
