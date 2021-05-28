package isa9.Farmacy.repository;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Pharmacy;
import isa9.Farmacy.model.PharmacyAdmin;
import isa9.Farmacy.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long>  {
    List<Vacation> findByDoctor(Doctor doctor);
    List<Vacation> findByPharmacyAdmin(PharmacyAdmin pharmacyAdmin);
    List<Vacation> findByPharmacy(Pharmacy pharmacy);
}
