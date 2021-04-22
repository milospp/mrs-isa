package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findFirstByDoctorAndExamination_PatientAndExamination_Status(Doctor doctor, Patient patient, ExaminationStatus status);
    Appointment findFirstByPharmacyAndExamination_PatientAndExamination_Status(Pharmacy pharmacy, Patient patient, ExaminationStatus status);
}
