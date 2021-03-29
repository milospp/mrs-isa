package isa9.Farmacy.support;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorToDoctorDTO implements Converter<Doctor, DoctorDTO> {

    @Override
    public DoctorDTO convert(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();

        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSurname(doctor.getSurname());
        dto.setAddress(doctor.getAddress());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setEmail(doctor.getEmail());
        return dto;
    }

    public List<DoctorDTO> convert(List<Doctor> doctors) {
        List<DoctorDTO> dto = new ArrayList<>();
        for (Doctor doctor : doctors){
            dto.add(convert(doctor));
        }
        return dto;
    }
}
