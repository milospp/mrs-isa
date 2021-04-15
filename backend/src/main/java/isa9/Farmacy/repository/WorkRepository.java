package isa9.Farmacy.repository;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Work;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByDoctor(Doctor doctor);
}
