package isa9.Farmacy;

import isa9.Farmacy.model.Appointment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@RestController
@SpringBootApplication
@EnableScheduling
public class FarmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmacyApplication.class, args);
	}

}
