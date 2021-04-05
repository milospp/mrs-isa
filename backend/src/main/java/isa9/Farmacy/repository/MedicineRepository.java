package isa9.Farmacy.repository;

import isa9.Farmacy.model.Examination;
import isa9.Farmacy.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
