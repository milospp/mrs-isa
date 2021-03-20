package isa9.service;

import isa9.model.Patient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class PatientHardcodedService {
    private static List<Patient> patients = new ArrayList<>();
    private static long idCounter = 0;

    static {
        patients.add(new Patient(++idCounter, "isika", "Isidora", "Stanic"));
        patients.add(new Patient(++idCounter, "micika", "Milica", "Djumic"));
        patients.add(new Patient(++idCounter, "mladenko", "Mladen", "Vasic"));
        patients.add(new Patient(++idCounter, "losmi", "Milos", "Popovic"));
    }

    public List<Patient> findAll() {
        System.out.println(patients);
        return patients;
    }
}
