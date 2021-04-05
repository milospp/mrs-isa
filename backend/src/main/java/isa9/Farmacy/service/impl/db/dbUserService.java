package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.PharmacyAdmin;
import isa9.Farmacy.model.User;
import isa9.Farmacy.repository.UserRepository;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Primary
@Service
public class dbUserService extends UserServiceBase implements UserService {

    private UserRepository userRepository;

    @Autowired
    public dbUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        return this.userRepository.findById(id).orElseGet(null);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public boolean isAvaibleEmail(String em) {
        return true;
//        throw new NotYetImplementedException();
//        return false;
    }

    @Override
    public PharmacyAdmin findPharmacyAdmin(Long pharmacyId) {
//        return true;
        System.out.println("FindPharmacyAdmin Not implemented");
        throw new NotYetImplementedException();
//        return null;
    }
}
