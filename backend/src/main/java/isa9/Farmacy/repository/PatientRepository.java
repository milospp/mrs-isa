package isa9.Farmacy.repository;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE (SELECT COUNT(e) FROM Examination e WHERE e.appointment.doctor.id = :doctorId AND e.patient = p) > 0 AND LOWER(p.name) like CONCAT('%',LOWER(:name),'%') AND LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%')")
    Page<Patient> findPatientsByDoctorIdAndSearch(@Param("doctorId") Long doctorId, @Param("name") String name, @Param("surname") String surname, Pageable paging);

    @Query("SELECT COUNT(p) FROM Patient p WHERE (SELECT COUNT(e) FROM Examination e WHERE e.appointment.doctor.id = :doctorId AND e.patient = p) > 0 AND LOWER(p.name) like CONCAT('%',LOWER(:name),'%') AND LOWER(p.surname) like CONCAT('%',LOWER(:surname),'%')")
    long countFoundPatientsByDoctorIdAndSearch(Long doctorId, String name, String surname);

    Page<Patient> findByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname, Pageable paging);

    long countByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname);
}
