package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findFirstByDoctorAndExamination_PatientAndExamination_Status(Doctor doctor, Patient patient, ExaminationStatus status);
    Appointment findFirstByPharmacyAndExamination_PatientAndExamination_Status(Pharmacy pharmacy, Patient patient, ExaminationStatus status);

    @Query(value="SELECT * FROM Appointment ap WHERE ap.start_time <= cast(?1 AS timestamp) and ap.start_time + (TIME '00:01' * ap.duration_in_mins) >= cast(?1 AS timestamp)" +
            "or (ap.start_time <= cast(?2 AS timestamp) and ap.start_time + (TIME '00:01' * ap.duration_in_mins) >= cast(?2 AS timestamp))" +
            "or (ap.start_time >= cast(?1 AS timestamp) and ap.start_time + (TIME '00:01' * ap.duration_in_mins) <= cast(?2 AS timestamp))", nativeQuery = true)
    List<Appointment> getAppointmentsInInterval(String start, String end);

    Appointment findByStartTime(LocalDateTime last);
}
