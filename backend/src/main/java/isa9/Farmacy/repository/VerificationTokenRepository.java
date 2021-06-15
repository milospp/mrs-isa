package isa9.Farmacy.repository;

import isa9.Farmacy.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface VerificationTokenRepository extends JpaRepository <VerificationToken, Long> {

}
