package isa9.Farmacy.service.impl.db;

import isa9.Farmacy.model.EPrescription;
import isa9.Farmacy.repository.EPrescriptionRepository;
import isa9.Farmacy.service.EPrescriptionService;
import isa9.Farmacy.service.impl.base.EPrescriptionServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Primary
@Service
public class dbEPrescriptionService extends EPrescriptionServiceBase implements EPrescriptionService {

    private final EPrescriptionRepository ePrescriptionRepository;

    @Autowired
    public dbEPrescriptionService(EPrescriptionRepository ePrescriptionRepository) {
        this.ePrescriptionRepository = ePrescriptionRepository;
    }

    @Override
    public List<EPrescription> findAll() {
        return this.ePrescriptionRepository.findAll();
    }

    @Override
    public EPrescription findOne(Long id) {
        return this.ePrescriptionRepository.findById(id).orElse(null);
    }

    @Override
    public EPrescription save(EPrescription entity) {
        return this.ePrescriptionRepository.save(entity);
    }
}
