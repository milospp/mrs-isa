package isa9.Farmacy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@CrossOrigin(origins = {"http://localhost:3000", "https://pharmacy9.herokuapp.com"})
@RestController
public class IndexController {

        @GetMapping(path="/", produces="application/json")
        public ResponseEntity<String> getAllPatients() {
            return new ResponseEntity<String>("Deploy test #5", HttpStatus.OK);
        }
    }
