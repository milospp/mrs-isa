package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedicineQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedQuantityRepository  extends JpaRepository<MedicineQuantity, Long> {
}
