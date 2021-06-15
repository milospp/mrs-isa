package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.VerificationToken;
import isa9.Farmacy.repository.VerificationTokenRepository;
import isa9.Farmacy.service.VerificationTokenService;
import isa9.Farmacy.service.impl.base.VerificationTokenServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbVerificationTokenService extends VerificationTokenServiceBase implements VerificationTokenService {

    private final VerificationTokenRepository repository;

    @Autowired
    public dbVerificationTokenService(VerificationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VerificationToken> findAll() {
        return this.repository.findAll();
    }

    @Override
    public VerificationToken findOne(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public VerificationToken save(VerificationToken entity) {
        return this.repository.save(entity);
    }
}
