package isa9.Farmacy.support;

import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DoctorDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorToDoctorDTO implements Converter<Doctor, DoctorDTO> {

    @Override
    public DoctorDTO convert(Doctor doktor) {
        DoctorDTO dto = new DoctorDTO();

        dto.setId(doktor.getId());
        dto.setName(doktor.getName());
        dto.setSurname(doktor.getSurname());
        dto.setAddress(doktor.getAddress());
        dto.setPhoneNumber(doktor.getPhoneNumber());
        return dto;
    }

    public List<DoctorDTO> convert(List<Doctor> doktori) {
        List<DoctorDTO> dto = new ArrayList<>();
        for (Doctor d : doktori){
            dto.add(convert(d));
        }
        return dto;
    }
}
