package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingDoctorRepository extends JpaRepository<RatingDoctor, Long> {
    RatingDoctor findByUserAndAndDoctor(User user, Doctor doctor);

//    @Query("SELECT NULLIF(AVG(ratings.rating), -1) FROM RatingDoctor ratings WHERE ratings.doctor = ?1")
//    Double getAverageDoctorRating(Doctor doctor);

    @Query("SELECT NULLIF(AVG(ratings.rating), -1) FROM RatingDoctor ratings WHERE ratings.doctor.id = ?1")
    Double getAverageDoctorRating(Long doctorId);

}
