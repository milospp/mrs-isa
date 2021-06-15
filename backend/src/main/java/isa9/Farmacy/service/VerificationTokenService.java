package isa9.Farmacy.service;

import isa9.Farmacy.model.VerificationToken;

public interface VerificationTokenService extends GenericService<VerificationToken> {
    VerificationToken findByToken(String token);
}
