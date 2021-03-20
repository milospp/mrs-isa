package isa9.controller;

import isa9.model.Patient;
import isa9.service.PatientHardcodedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class IndexController {

        @GetMapping(path="/", produces="application/json")
        public ResponseEntity<String> getAllPatients() {
            return new ResponseEntity<String>("Hello", HttpStatus.OK);
        }
    }
