package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedicineAtSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedAtSupplierRepository extends JpaRepository<MedicineAtSupplier, Long> {
}
