package isa9.Farmacy.support;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.PatientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientToPatientDTO implements Converter<Patient, PatientDTO> {

    @Override
    public PatientDTO convert(Patient patient) {
        PatientDTO dto = new PatientDTO();

        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setSurname(patient.getSurname());
        dto.setAddress(patient.getAddress());
        dto.setPhoneNumber(patient.getPhoneNumber());
        dto.setEmail(patient.getEmail());
        dto.setPoints(patient.getPoints());
        dto.setPenalties(
                patient.getPenalties().stream()
                        .map(Penality::getId)
                        .collect(Collectors.toList())
        );
        dto.setSubscriptions(
                patient.getSubscriptions().stream()
                        .map(Pharmacy::getId)
                        .collect(Collectors.toList())
        );
        dto.setMyExaminations(
                patient.getMyExaminations().stream()
                        .map(Examination::getId)
                        .collect(Collectors.toList())
        );
        dto.setAllergies(
                patient.getAllergies().stream()
                        .map(Medicine::getCode)
                        .collect(Collectors.toList())
        );
        dto.setReservations(
                patient.getReservations().stream()
                        .map(MedReservation::getId)
                        .collect(Collectors.toList())
        );


        return dto;
    }

    public List<PatientDTO> convert(List<Patient> patients) {
        List<PatientDTO> dto = new ArrayList<>();
        for (Patient patient : patients){
            dto.add(convert(patient));
        }
        return dto;
    }
}
