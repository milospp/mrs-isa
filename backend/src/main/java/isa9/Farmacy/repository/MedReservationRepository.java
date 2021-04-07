package isa9.Farmacy.repository;

import isa9.Farmacy.model.MedReservation;
import isa9.Farmacy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedReservationRepository extends JpaRepository<MedReservation, Long> {


}
