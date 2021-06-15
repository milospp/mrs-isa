package isa9.Farmacy.service.impl.base;

import isa9.Farmacy.model.VerificationToken;
import isa9.Farmacy.service.VerificationTokenService;

public abstract class VerificationTokenServiceBase implements VerificationTokenService {
    @Override
    public VerificationToken findByToken(String token) {
        for(VerificationToken vt : this.findAll()){
            if(vt.getToken().equals(token)) return vt;
        }

        return null;
    }
}
