package isa9.Farmacy;

import isa9.model.Patient;
import isa9.service.PatientHardcodedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class PatientController {

//    @Autowired
//    private PatientHardcodedService patientManagementService;

    @RequestMapping(value="/patients", produces="application/json")
    public ResponseEntity<List<Patient>> getAllPatients() {
        PatientHardcodedService patientManagementService = new PatientHardcodedService();
        return new ResponseEntity<List<Patient>>(patientManagementService.findAll(), HttpStatus.OK);
    }
}
