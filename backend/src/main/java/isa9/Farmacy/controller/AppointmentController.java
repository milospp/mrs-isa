package isa9.Farmacy.controller;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.dto.AppointmentDTO;
import isa9.Farmacy.model.dto.PharmacyDTO;
import isa9.Farmacy.service.AppointmentService;
import isa9.Farmacy.service.PharmacyService;
import isa9.Farmacy.support.AppointmentToAppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentToAppointmentDTO appointmentToAppointmentDTO;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentToAppointmentDTO appointmentToAppointmentDTO){
        this.appointmentService = appointmentService;
        this.appointmentToAppointmentDTO = appointmentToAppointmentDTO;
    }


    @GetMapping("")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> resultDTOS = appointmentToAppointmentDTO.convert(this.appointmentService.findAll());

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

}

