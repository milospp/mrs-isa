package isa9.Farmacy.repository;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import isa9.Farmacy.model.dto.PatientIdLastDateDTO;
import isa9.Farmacy.model.dto.PatientLastAppointmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE (SELECT COUNT(e) FROM Examination e WHERE e.appointment.doctor.id = :doctorId AND e.patient = p AND e.status != 3) > 0 AND LOWER(p.name) like CONCAT('%',LOWER(:name),'%') AND LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%')")
    Page<Patient> findPatientsByDoctorIdAndSearch(@Param("doctorId") Long doctorId, @Param("name") String name, @Param("surname") String surname, Pageable paging);

    @Query("SELECT COUNT(p) FROM Patient p WHERE (SELECT COUNT(e) FROM Examination e WHERE e.appointment.doctor.id = :doctorId AND e.patient = p AND e.status != 3) > 0 AND LOWER(p.name) like CONCAT('%',LOWER(:name),'%') AND LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%')")
    long countFoundPatientsByDoctorIdAndSearch(Long doctorId, String name, String surname);

    Page<Patient> findByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname, Pageable paging);

    long countByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname);

    @Query(value = "select id, last FROM " +
            "(select distinct users.*, max(appointment.start_time) as last " +
            "FROM users, appointment, examination " +
            "where users.id = examination.patient_id and examination.appointment_id = appointment.id " +
            "and appointment.doctor_id = :doctorId and examination.status != 3 " +
            "and LOWER(users.name) like CONCAT('%',LOWER(:name),'%') " +
            "and LOWER(users.surname) like CONCAT('%',LOWER(:surname),'%') "+
            "group by users.id) as pap",
            nativeQuery = true)
    Page<PatientIdLastDateDTO> findNotSorted(@Param("doctorId") Long doctorId, @Param("name") String name, @Param("surname") String surname, Pageable paging);

    @Query(value = "select count(pap.*) from " +
            "(select distinct p.*, max(a.start_time) as last " +
            "from users as p, appointment as a, examination as e " +
            "where p.id = e.patient_id and e.appointment_id = a.id " +
            "and a.doctor_id = :doctorId and e.status != 3 " +
            "and LOWER(p.name) like CONCAT('%',LOWER(:name),'%') " +
            "and LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%') "+
            "group by p.id) as pap",
            nativeQuery = true)
    long findNotSortedCount(@Param("doctorId") Long doctorId, @Param("name") String name, @Param("surname") String surname);


}
