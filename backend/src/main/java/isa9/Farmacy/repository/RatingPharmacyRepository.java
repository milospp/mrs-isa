package isa9.Farmacy.repository;

import isa9.Farmacy.model.Rating;
import isa9.Farmacy.model.RatingPharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingPharmacyRepository extends JpaRepository<RatingPharmacy, Long> {

}
