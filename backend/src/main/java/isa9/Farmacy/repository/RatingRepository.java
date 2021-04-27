package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
