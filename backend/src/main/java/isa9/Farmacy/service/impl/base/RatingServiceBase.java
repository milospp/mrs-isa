package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.*;
import isa9.Farmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class RatingServiceBase implements RatingService {
    protected UserService userService;
    protected AppointmentService appointmentService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public Rating rateDoctor(Long doctorId, Long userId, int rate) {
        Doctor doctor = userService.getDoctorById(doctorId);
        Patient patient = userService.getPatientById(userId);
        if (doctor == null || patient == null) return null;

        Rating rating = getPatientDoctorRate(patient, doctor);

        if (appointmentService.patientCanRateDoctor(patient,doctor)){
            rating.setRating(rate);
            return save(rating);
        }

        return null;
    }
}