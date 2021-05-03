package isa9.Farmacy.repository;

import isa9.Farmacy.model.Medicine;
import isa9.Farmacy.model.MedicineInPharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedInPharmaRepository extends JpaRepository<MedicineInPharmacy, Long> {
}
