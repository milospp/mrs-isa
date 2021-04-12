package isa9.Farmacy.repository;

import isa9.Farmacy.model.Patient;
import isa9.Farmacy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname, Pageable paging);

    long countByNameIgnoreCaseContainingAndSurnameIgnoreCaseContaining(String name, String surname);
}
