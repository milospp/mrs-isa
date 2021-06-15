package isa9.Farmacy.repository;

import isa9.Farmacy.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;


@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Pharmacy p WHERE p.id = :id")
    Pharmacy findPharmacyByIdLocked(Long id);
}
