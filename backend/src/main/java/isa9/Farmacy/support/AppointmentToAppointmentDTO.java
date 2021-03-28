package isa9.Farmacy.support;


import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.AppointmentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AppointmentToAppointmentDTO implements Converter<Appointment, AppointmentDTO> {

    @Override
    public AppointmentDTO convert(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(appointment.getId());
        dto.setStartTime(appointment.getStartTime());
        dto.setPrice(appointment.getPrice());
        dto.setDurationInMins(appointment.getDurationInMins());
        dto.setExamination(appointment.getExamination());
        dto.setType(appointment.getType());
        dto.setDoctor(appointment.getDoctor());
        dto.setPharmacy(appointment.getPharmacy());


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
