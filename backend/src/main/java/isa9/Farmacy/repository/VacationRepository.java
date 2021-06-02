package isa9.Farmacy.repository;

import isa9.Farmacy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long>  {
    List<Vacation> findByDoctor(Doctor doctor);
    List<Vacation> findByAdmin(User admin);
    List<Vacation> findByPharmacy(Pharmacy pharmacy);
}
