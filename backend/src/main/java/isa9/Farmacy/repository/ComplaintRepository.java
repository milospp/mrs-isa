package isa9.Farmacy.repository;

import isa9.Farmacy.model.Complaint;
import isa9.Farmacy.model.MedReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Complaint c WHERE c.id = :id")
    Complaint findByIdLocked(@Param("id") Long id);
}
