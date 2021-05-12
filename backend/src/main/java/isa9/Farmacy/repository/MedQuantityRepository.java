package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedicineQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedQuantityRepository  extends JpaRepository<MedicineQuantity, Long> {
}
