package isa9.Farmacy.controller;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.PatientDTO;
import isa9.Farmacy.model.dto.UserDTO;
import isa9.Farmacy.service.PatientService;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.inmemory.InMemoryPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @RequestMapping(value="/patients", produces="application/json")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("all-patients")
    public ResponseEntity<List<PatientDTO>> getAllUsers() {
        List<PatientDTO> resultDTOS = new ArrayList<>();
        for (Patient patient : this.patientService.findAll()){
            resultDTOS.add(new PatientDTO(patient.getId(), patient.getUsername(), patient.getName(), patient.getSurname(), patient.getAddress().toString(), patient.getPhoneNumber()));
        }

        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);

    }

    @GetMapping("patient-{id}")
    public ResponseEntity<PatientDTO> getUser(@PathVariable Long id){
        Patient patient = patientService.findOne(id);

        PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getUsername(), patient.getName(), patient.getSurname(), patient.getAddress().toString(), patient.getPhoneNumber());
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);

    }


    @PostMapping("register-patient")
    public ResponseEntity<PatientDTO> registerUser(@RequestBody Patient patient) {
        patientService.save(patient);
        return new ResponseEntity<>(
                new PatientDTO(patient.getId(), patient.getUsername(), patient.getName(), patient.getSurname(), patient.getAddress().toString(), patient.getPhoneNumber()),
                HttpStatus.OK
        );

    }
}
