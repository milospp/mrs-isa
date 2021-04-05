package isa9.Farmacy.repository;

import isa9.Farmacy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
