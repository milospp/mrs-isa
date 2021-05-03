package isa9.Farmacy.repository;

import isa9.Farmacy.model.Dermatologist;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
