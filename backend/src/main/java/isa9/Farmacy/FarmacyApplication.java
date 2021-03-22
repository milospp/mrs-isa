package isa9.Farmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class FarmacyApplication {

//	@RequestMapping(value="/patients", produces="application/json")
//	public ResponseEntity<List<Patient>> getAllPatients() {
//		PatientHardcodedService patientManagementService = new PatientHardcodedService();
//		return new ResponseEntity<List<Patient>>(patientManagementService.findAll(), HttpStatus.OK);
//	}

	public static void main(String[] args) {
//		// System.out.println("Ajmo piti ajmo zagalamit!");
//		PatientHardcodedService ps = new PatientHardcodedService();
//		System.out.println(ps.findAll());
		SpringApplication.run(FarmacyApplication.class, args);
	}

}
