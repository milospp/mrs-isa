package isa9.Farmacy.service.impl.inmemory;

import isa9.Farmacy.model.User;
import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.impl.base.UserServiceBase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryUserService extends UserServiceBase implements UserService {

    private final Map<Long, User> users = new HashMap<>();


//    InMemoryUserService() {
//        users.put(1L, new User(1L, "2", "3", "4"));
//        users.put(2L, new User(2L, "2", "3", "4"));
//        users.put(3L, new User(3L, "2", "3", "4"));
//        users.put(4L, new User(4L, "2", "3", "4"));
//    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.users.values());
    }

    @Override
    public User findOne(Long id) {
        return this.users.getOrDefault(id, null);
    }

    @Override
    public User save(User entity) {
        this.users.put(entity.getId(), entity);
        return entity;
    }


}
