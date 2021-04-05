package isa9.Farmacy.repository;

import isa9.Farmacy.model.Appointment;
import isa9.Farmacy.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
