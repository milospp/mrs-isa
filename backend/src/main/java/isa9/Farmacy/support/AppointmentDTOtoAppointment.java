package isa9.Farmacy.support;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class AppointmentDTOtoAppointment implements Converter<AppointmentDTO, Appointment>  {

    private UserService userService;
    private PharmacyService pharmacyService;

    @Autowired
    public AppointmentDTOtoAppointment(UserService userService, PharmacyService pharmacyService) {
        this.userService = userService;
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Appointment convert(AppointmentDTO appointmentDTO) {
        Appointment povratna = new Appointment();
        Doctor doktor = (Doctor) this.userService.findOne(appointmentDTO.getDoctor().getId());
        povratna.setDoctor(doktor);
        povratna.setDurationInMins(appointmentDTO.getDurationInMins());
        povratna.setPrice(appointmentDTO.getPrice());
        povratna.setStartTime(appointmentDTO.getStartTime());
        povratna.setType(appointmentDTO.getType());
        Pharmacy apoteka = this.pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
        povratna.setPharmacy(apoteka);
        return povratna;
    }
}
