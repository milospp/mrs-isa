package isa9.Farmacy.service;

import isa9.Farmacy.model.User;

public interface UserService extends GenericService<User>{

    boolean isAvaibleEmail(String em);

}
