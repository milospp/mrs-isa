package isa9.Farmacy.constants;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.UserRole;

import java.util.HashSet;

public class UserConstants {
    public static Patient getPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setRole(new UserRole(1L, "PATIENT"));
        patient.setAllergies(new HashSet<>());
        patient.setReservations(new HashSet<>());

        return patient;
    }
}
