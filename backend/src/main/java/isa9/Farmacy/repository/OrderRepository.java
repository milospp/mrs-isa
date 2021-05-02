package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedicineOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<MedicineOrder, Long> {
}
