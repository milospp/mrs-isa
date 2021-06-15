package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.service.UserService;
import isa9.Farmacy.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VacationServiceBase implements VacationService {
    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
