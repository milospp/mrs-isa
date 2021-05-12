package isa9.Farmacy.repository;

import isa9.Farmacy.model.InquiryMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryMedRepository extends JpaRepository<InquiryMedicine, Long> {
}
