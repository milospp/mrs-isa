package isa9.Farmacy.support;


import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.AppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppointmentToAppointmentDTO implements Converter<Appointment, AppointmentDTO> {

    ExaminationToExaminationDTO examinationToExaminationDTO;
    DoctorToDoctorDTO doctorToDoctorDTO;
    PharmacyToPharmacyDTO pharmacyToPharmacyDTO;

    @Autowired
    AppointmentToAppointmentDTO(ExaminationToExaminationDTO examinationToExaminationDTO, DoctorToDoctorDTO doctorToDoctorDTO, PharmacyToPharmacyDTO pharmacyToPharmacyDTO) {
        this.examinationToExaminationDTO = examinationToExaminationDTO;
        this.doctorToDoctorDTO = doctorToDoctorDTO;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
    }

    @Override
    public AppointmentDTO convert(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(appointment.getId());
        dto.setStartTime(appointment.getStartTime());
        dto.setPrice(appointment.getPrice());
        dto.setDurationInMins(appointment.getDurationInMins());
        dto.setExamination(examinationToExaminationDTO.convert(appointment.getExamination()));
        dto.setType(appointment.getType());
        dto.setDoctor(doctorToDoctorDTO.convert(appointment.getDoctor()));
        dto.setPharmacy(pharmacyToPharmacyDTO.convert(appointment.getPharmacy()));


        return dto;
    }

    public List<AppointmentDTO> convert(List<Appointment> appointments) {
        List<AppointmentDTO> dto = new ArrayList<>();
        for (Appointment appointment : appointments){
            dto.add(convert(appointment));
        }
        return dto;
    }
    public List<AppointmentDTO> convert(Set<Appointment> appointments) {
        List<AppointmentDTO> dto = new ArrayList<>();
        for (Appointment appointment : appointments){
            dto.add(convert(appointment));
        }
        return dto;
    }

}
