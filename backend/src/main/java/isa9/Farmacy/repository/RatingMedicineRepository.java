package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingMedicineRepository extends JpaRepository<RatingMedicine, Long> {
    RatingMedicine findFirstByUserAndMedicine(User user, Medicine medicine);

//    @Query("SELECT NULLIF(AVG(ratings.rating), -1) FROM RatingDoctor ratings WHERE ratings.doctor = ?1")
//    Double getAverageDoctorRating(Doctor doctor);

    @Query("SELECT NULLIF(AVG(ratings.rating), -1) FROM RatingMedicine ratings WHERE ratings.medicine.id = ?1")
    Double getAverageMedicineRating(Long medicineId);
}
