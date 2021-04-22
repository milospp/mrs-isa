package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingPharmacyRepository extends JpaRepository<RatingPharmacy, Long> {
    RatingPharmacy findFirstByUserAndPharmacy(User user, Pharmacy pharmacy);

    @Query("SELECT NULLIF(AVG(ratings.rating), -1) FROM RatingPharmacy ratings WHERE ratings.pharmacy.id = ?1")
    Double getAveragePharmacyRating(Long pharmacyId);
}
