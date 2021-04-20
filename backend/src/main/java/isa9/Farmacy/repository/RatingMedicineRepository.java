package isa9.Farmacy.repository;

import isa9.Farmacy.model.Rating;
import isa9.Farmacy.model.RatingMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingMedicineRepository extends JpaRepository<RatingMedicine, Long> {

}
