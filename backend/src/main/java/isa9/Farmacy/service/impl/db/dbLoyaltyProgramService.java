package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.LoyaltyProgram;
import isa9.Farmacy.repository.LoyaltyProgramRepository;
import isa9.Farmacy.service.LoyaltyProgramService;
import isa9.Farmacy.service.impl.base.LoyaltyProgramServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbLoyaltyProgramService extends LoyaltyProgramServiceBase implements LoyaltyProgramService {

    private final LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    public dbLoyaltyProgramService(LoyaltyProgramRepository loyaltyProgramRepository) {
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }

    @Override
    public List<LoyaltyProgram> findAll() {
        return this.loyaltyProgramRepository.findAll();
    }

    @Override
    public LoyaltyProgram findOne(Long id) {
        return this.loyaltyProgramRepository.findById(id).orElse(null);
    }

    @Override
    public LoyaltyProgram save(LoyaltyProgram entity) {
        return this.loyaltyProgramRepository.save(entity);
    }
}
