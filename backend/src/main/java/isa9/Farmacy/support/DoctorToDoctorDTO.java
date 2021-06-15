package isa9.Farmacy.support;


import isa9.Farmacy.model.*;
import isa9.Farmacy.model.dto.DoctorDTO;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.RolesDTO;
import isa9.Farmacy.model.dto.WorkDTO;
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
        dto.setRating(doctor.getRating());
        if(doctor.getRole().getName().equals("PHARMACIST")){
            dto.setRole(RolesDTO.PHARMACIST);
        }else if(doctor.getRole().getName().equals("DERMATOLOGIST")) {
            dto.setRole(RolesDTO.DERMATOLOGIST);
        }
        return dto;
    }

    public List<DoctorDTO> convert(List<Doctor> doctors) {
        List<DoctorDTO> dto = new ArrayList<>();
        for (Doctor doctor : doctors){
            dto.add(convert(doctor));
        }
        return dto;
    }

    public DoctorDTO convert(Doctor doctor, Work workInPharmacy) {
        DoctorDTO dto = new DoctorDTO();

        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSurname(doctor.getSurname());
        dto.setAddress(doctor.getAddress());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setEmail(doctor.getEmail());
        dto.setRating(doctor.getRating());
        if(doctor.getRole().getName().equals("PHARMACIST")){
            dto.setRole(RolesDTO.PHARMACIST);
        }else if(doctor.getRole().getName().equals("DERMATOLOGIST")){
            dto.setRole(RolesDTO.DERMATOLOGIST);
        }

        WorkDTO posaoUApoteci = new WorkDTO();
        posaoUApoteci.setEndHour(workInPharmacy.getEndHour());
        posaoUApoteci.setStartHour(workInPharmacy.getStartHour());
        posaoUApoteci.setPharmacyId(workInPharmacy.getPharmacy().getId());
        posaoUApoteci.setPharmacyName(workInPharmacy.getPharmacy().getName());
        dto.setPharmacyWork(posaoUApoteci);
        return dto;
    }
}
