package isa9.Farmacy.constants;

import isa9.Farmacy.model.*;

import java.util.HashSet;

public class UserConstants {
    public static Patient getPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setRole(new UserRole(3L, "PATIENT"));
        patient.setAllergies(new HashSet<>());
        patient.setReservations(new HashSet<>());

        return patient;
    }

    public static Pharmacist getPharmacist() {
        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setId(11L);
        pharmacist.setRole(new UserRole(5L, "PHARMACIST"));
        return pharmacist;
    }

    public static Dermatologist getDermatologist() {
        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setId(21L);
        dermatologist.setRole(new UserRole(4L, "DERMATOLOGIST"));
        return dermatologist;
    }
}
