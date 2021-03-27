package isa9.Farmacy.service;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.User;

import java.util.Set;

public interface UserService extends GenericService<User> {

    boolean isAvaibleEmail(String em);

    Set<Medicine> getPatientAllergies(User patient);

}
