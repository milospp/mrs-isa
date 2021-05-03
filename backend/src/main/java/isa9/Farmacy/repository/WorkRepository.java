package isa9.Farmacy.repository;

import isa9.Farmacy.model.Doctor;
import isa9.Farmacy.model.Work;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByDoctor(Doctor doctor);

    @Query("SELECT w FROM Work w WHERE ((w.endHour < w.startHour) and (w.startHour < cast(?1 AS time) or w.endHour > cast(?1 AS time))) or ((w.endHour > w.startHour) and (w.startHour < cast(?1 AS time) and w.endHour > cast(?1 AS time)))")
    List<Work> getAllWorksByTime(String time);

}
