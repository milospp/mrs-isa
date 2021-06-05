package isa9.Farmacy.repository;

import isa9.Farmacy.model.Dermatologist;
import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query("SELECT d FROM Doctor d WHERE d.id = :id")
    Doctor findDoctorByIdLocked(@Param("id") Long id);

}
