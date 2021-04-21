package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.UserRole;
import isa9.Farmacy.repository.UserRepository;
import isa9.Farmacy.repository.UserRoleRepository;
import isa9.Farmacy.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbUserRoleService implements UserRoleService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public dbUserRoleService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> findAll() {
        return this.userRoleRepository.findAll();
    }

    @Override
    public UserRole findOne(Long id) {
        return this.userRoleRepository.findById(id).orElse(null);
    }

    @Override
    public UserRole save(UserRole entity) {
        return this.userRoleRepository.save(entity);
    }
}
