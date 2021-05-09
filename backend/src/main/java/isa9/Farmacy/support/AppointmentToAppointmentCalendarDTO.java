package isa9.Farmacy.support;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.ExaminationStatus;
import isa9.Farmacy.model.TypeOfReview;
import isa9.Farmacy.model.dto.AppointmentCalendarDTO;
import isa9.Farmacy.model.dto.AppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AppointmentToAppointmentCalendarDTO implements Converter<Appointment, AppointmentCalendarDTO> {

    ExaminationToExaminationDTO examinationToExaminationDTO;
    DoctorToDoctorDTO doctorToDoctorDTO;
    PharmacyToPharmacyDTO pharmacyToPharmacyDTO;

    @Autowired
    AppointmentToAppointmentCalendarDTO(ExaminationToExaminationDTO examinationToExaminationDTO, DoctorToDoctorDTO doctorToDoctorDTO, PharmacyToPharmacyDTO pharmacyToPharmacyDTO) {
        this.examinationToExaminationDTO = examinationToExaminationDTO;
        this.doctorToDoctorDTO = doctorToDoctorDTO;
        this.pharmacyToPharmacyDTO = pharmacyToPharmacyDTO;
    }

    @Override
    public AppointmentCalendarDTO convert(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentCalendarDTO dto = new AppointmentCalendarDTO();

        dto.setId(appointment.getId());
        dto.setStartTime(appointment.getStartTime().toLocalTime());
        dto.setStartDate(appointment.getStartTime().toLocalDate());
        dto.setDurationInMins(appointment.getDurationInMins());
        dto.setPharmacyName(appointment.getPharmacy().getName());
        if (appointment.getExamination() == null)
            dto.setTypeForClass("free");
        else {
            dto.setTypeForClass("");
            if (appointment.getExamination().getStatus() == ExaminationStatus.HELD)
                dto.setTypeForClass(dto.getTypeForClass() + " held");
        }
        if (appointment.getType() == TypeOfReview.EXAMINATION){
            dto.setTypeForClass(dto.getTypeForClass() + " examination");
        } else {
            dto.setTypeForClass(dto.getTypeForClass() + " counseling");
        }

        if (!dto.getTypeForClass().contains("free")) {
            dto.setPatientName(appointment.getExamination().getPatient().getName());
            dto.setPatientSurname(appointment.getExamination().getPatient().getSurname());
        } else {
            dto.setPatientName("");
            dto.setPatientSurname("");
        }

        return dto;
    }

    public List<AppointmentCalendarDTO> convert(List<Appointment> appointments) {
        List<AppointmentCalendarDTO> dto = new ArrayList<>();
        for (Appointment appointment : appointments){
            dto.add(convert(appointment));
        }
        return dto;
    }
    public List<AppointmentCalendarDTO> convert(Set<Appointment> appointments) {
        List<AppointmentCalendarDTO> dto = new ArrayList<>();
        for (Appointment appointment : appointments){
            dto.add(convert(appointment));
        }
        return dto;
    }

}
